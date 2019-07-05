package com.jackssy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jackssy.admin.entity.BzProductShort;
import com.jackssy.admin.entity.vo.ProductStatusEnum;
import com.jackssy.admin.service.BzProductShortService;
import com.jackssy.admin.service.UserService;
import com.jackssy.common.annotation.SysLog;
import com.jackssy.common.base.PageData;
import com.jackssy.common.config.MySysUser;
import com.jackssy.common.util.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("admin/product")
public class ProductController {
    @Autowired
    private BzProductShortService productShortService;

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

    @GetMapping("add")
    public String add(ModelMap modelMap){
        return "admin/product/add";
    }

    @PostMapping("add")
    @ResponseBody
    @SysLog("保存新增系统用户数据")
    public ResponseEntity add(@RequestBody BzProductShort bzProductShort){
        bzProductShort.setGmtCreate(new Date());
        bzProductShort.setGmtModified(new Date());
        bzProductShort.setStatus(ProductStatusEnum.CREATED.getCode());
        String userId = MySysUser.id();
        bzProductShort.setOperateUser(userId);
        productShortService.save(bzProductShort);
        if(bzProductShort.getProductId()!=0){
            new Thread(() ->
                    productShortService.batchTest(bzProductShort)).start();
        }

        return ResponseEntity.success("操作成功");
    }
}
