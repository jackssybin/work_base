package com.jackssy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jackssy.admin.entity.BzProductMobile;
import com.jackssy.admin.entity.BzProductShort;
import com.jackssy.admin.entity.BzTranslate;
import com.jackssy.admin.entity.vo.BzProductShortVo;
import com.jackssy.admin.entity.vo.BzTranslateVo;
import com.jackssy.admin.enumtype.ProductStatusEnum;
import com.jackssy.admin.service.BzProductMobileService;
import com.jackssy.admin.service.BzProductShortService;
import com.jackssy.admin.service.BzTranslateService;
import com.jackssy.admin.service.UserService;
import com.jackssy.common.annotation.SysLog;
import com.jackssy.common.base.PageData;
import com.jackssy.common.config.MySysUser;
import com.jackssy.common.util.ResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin/product")
public class ProductController {
    @Autowired
    private BzProductShortService productShortService;

    @Autowired
    private BzTranslateService translateService;

    @Autowired
    private BzProductMobileService productMobileService ;


    @Autowired
    private UserService userService;

    @GetMapping("list")
    @SysLog("跳转产品管理页面")
    public String list(){
        return "admin/product/list";
    }

    @PostMapping("list")
    @ResponseBody
    public PageData<BzProductShort> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                         @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                         ServletRequest request){
        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageData<BzProductShort> userPageData = new PageData<>();
        QueryWrapper<BzProductShort> userWrapper = new QueryWrapper<>();

        IPage<BzProductShort> userPage = productShortService.page(new Page<>(page,limit),userWrapper);
        userPageData.setCount(userPage.getTotal());
        userPageData.setData(userPage.getRecords());
        return userPageData;
    }

    @GetMapping("translateList")
    @SysLog("跳转产品管理页面")
    public String translateList(){
        return "admin/product/translateList";
    }

    @PostMapping("translateList")
    @ResponseBody
    public PageData<BzTranslateVo> translateList(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                         @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                         ServletRequest request){
        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageData<BzTranslateVo> userPageData = new PageData<>();
        QueryWrapper<BzTranslate> userWrapper = new QueryWrapper<>();
        if(!map.isEmpty()){
            String keys = (String) map.get("key");
            if(StringUtils.isNotBlank(keys)) {
                userWrapper.and(wrapper ->
                        wrapper.like("phone_number", keys)
                                );
            }
        }

        IPage<BzTranslate> userPage = translateService.page(new Page<>(page,limit),userWrapper);
        List<BzTranslateVo> list =userPage.getRecords().stream().map(xx ->{
            BzTranslateVo bzTranslateVo =new BzTranslateVo();
            BeanUtils.copyProperties(xx,bzTranslateVo);
            bzTranslateVo.setProductName(productShortService.getProductName(xx.getProductId()));
            return bzTranslateVo;
        }).collect(Collectors.toList());
        userPageData.setCount(userPage.getTotal());
        userPageData.setData(list);
        return userPageData;
    }


    @GetMapping("add")
    public String add(ModelMap modelMap){
        return "admin/product/add";
    }

    @PostMapping("add")
    @ResponseBody
    @SysLog("保存新增产品任务数据")
    public ResponseEntity add( BzProductShortVo bzProductShort){
        bzProductShort.setGmtCreate(new Date());
        bzProductShort.setGmtModified(new Date());
        bzProductShort.setStatus(ProductStatusEnum.CREATED.getCode());
        String userId = MySysUser.id();
        bzProductShort.setOperateUser(userId);
        if(!bzProductShort.getPhoneFile().isEmpty()){
            bzProductShort.setPhonePath(productShortService.savePhoneFile(bzProductShort.getPhoneFile()));
        }

        productShortService.save(bzProductShort);
        if(bzProductShort.getProductId()!=0){
            new Thread(() ->
                    productShortService.batchTest(bzProductShort)).start();
        }

        return ResponseEntity.success("操作成功");
    }


    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除系统用户数据(单个)")
    public ResponseEntity delete(@RequestParam(value = "productId",required = false)Integer productId){
        if(0==productId){
            return ResponseEntity.failure("参数错误");
        }
        BzProductShort bzProductShort =productShortService.getById(productId);
        if(bzProductShort.getStatus().equals(ProductStatusEnum.RUNNING.getCode())){
            return ResponseEntity.failure("跑批中的任务不能删除");
        }

        productShortService.removeById(productId);
        QueryWrapper<BzProductMobile> userWrapper = new QueryWrapper<>();
        userWrapper.eq("product_id", productId);
        productMobileService.remove(userWrapper);
        return ResponseEntity.success("操作成功");
    }


}
