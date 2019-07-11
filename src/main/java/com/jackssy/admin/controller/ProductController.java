package com.jackssy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jackssy.admin.entity.BzProductMobile;
import com.jackssy.admin.entity.BzProductShort;
import com.jackssy.admin.entity.BzTranslate;
import com.jackssy.admin.entity.vo.BzProductShortVo;
import com.jackssy.admin.entity.vo.BzTranslateVo;
import com.jackssy.admin.entity.vo.ProductTranslateVo;
import com.jackssy.admin.enumtype.ProductStatusEnum;
import com.jackssy.admin.excel.config.ExcelUtil;
import com.jackssy.admin.excel.controller.BzTranslateExportInfo;
import com.jackssy.admin.service.BzProductMobileService;
import com.jackssy.admin.service.BzProductShortService;
import com.jackssy.admin.service.BzTranslateService;
import com.jackssy.admin.service.UserService;
import com.jackssy.common.annotation.SysLog;
import com.jackssy.common.base.PageData;
import com.jackssy.common.config.MySysUser;
import com.jackssy.common.util.ResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin/product")
public class ProductController {

    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);

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
        if(!map.isEmpty()){
            String keys = (String) map.get("key");
            if(StringUtils.isNotBlank(keys)) {
                userWrapper.and(wrapper ->
                        wrapper.like("prodct_name", keys)
                );
            }
        }

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
    public PageData<ProductTranslateVo> translateList(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                                 @RequestParam(value = "limit",defaultValue = "20")Integer limit,
                                                 ServletRequest request){
        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageData<ProductTranslateVo> userPageData = new PageData<>();
        IPage<ProductTranslateVo> userPage =translateService.queryTranslateList(new Page<>(page,limit));
        userPageData.setData(userPage.getRecords());
        userPageData.setCount(userPage.getTotal());
        return userPageData;
    }

    @PostMapping("translateListDetail")
    @ResponseBody
    public PageData<BzTranslateVo> translateListDetail(@RequestParam(value = "page",defaultValue = "1")Integer page,
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
//            new Thread(() ->
//                    productShortService.batchTest(bzProductShort)).start();
            productShortService.produceQueue(bzProductShort);
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

        QueryWrapper<BzTranslate> translateWrapper = new QueryWrapper<>();
        BzTranslate bzTranslate =new BzTranslate();
        bzTranslate.setProductId(productId);
        translateWrapper.setEntity(bzTranslate);
        translateService.remove(translateWrapper);
        return ResponseEntity.success("操作成功");
    }

    @RequestMapping(value = "writeTranslateExcel", method = RequestMethod.GET)
    public void writeTranslateExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productId=request.getParameter("productId");
        if(com.alibaba.excel.util.StringUtils.isEmpty(productId)){
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("<script>alert('产品信息不能为空')</script>");
            return ;
        }


        QueryWrapper<BzTranslate> queryWrapper = new QueryWrapper<>();
        BzTranslate bzTranslate = new BzTranslate();
        bzTranslate.setProductId(Integer.parseInt(productId));
        queryWrapper.setEntity(bzTranslate);

        List<BzTranslate> bzTranslateList = translateService.list(queryWrapper);
        List<BzTranslateExportInfo> list=bzTranslateList.stream().map(xx ->{
            BzTranslateExportInfo bbb =new BzTranslateExportInfo();
            BeanUtils.copyProperties(xx,bbb);
            bbb.setProductName(productShortService.getProductName(xx.getProductId()));
            return bbb;
        }).collect(Collectors.toList());

        String fileName = "产品转化报表详情";
        String sheetName = "sheet";
        ExcelUtil.writeExcel(response, list, fileName, sheetName, new BzTranslateExportInfo());
    }

    @RequestMapping(value = "writeProductTxt", method = RequestMethod.GET)
    public void writeProductTxt(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String productIds=request.getParameter("productIds");
        if(com.alibaba.excel.util.StringUtils.isEmpty(productIds)){
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("<script>alert('产品信息不能为空')</script>");
            return ;
        }
        logger.info("downloadTxt param:{}",productIds);
        String[] productIdTemp=productIds.split(",");
        Integer[] productIdArray =new Integer[productIdTemp.length];
        for(int i=0 ;i<productIdTemp.length ;i++){
            productIdArray[i]=Integer.parseInt(productIdTemp[i]);
        }

        int phoneNumCount=productShortService.getProductCount(productIdArray[0]);
        if(phoneNumCount==0){
            logger.info("传入的产品手机号数量为空，无法导出");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("<script>alert('传入的产品手机号数量为空，无法导出')</script>");
            return ;
        }
        if(productIdArray.length>1){
            for(int j =1; j<productIdArray.length ;j++){
                if(phoneNumCount!=productShortService.getProductCount(productIdArray[j])){
                    logger.info("传入的产品手机号数量不匹配，无法导出");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write("<script>alert('传入的产品手机号数量不匹配，无法导出')</script>");
                    return ;
                }
            }
        }

//        productIdArray=new Integer[]{999,111};
        long beginTime =System.currentTimeMillis();
        Map<String,String> productExtMap= productMobileService.getProductExtMap(response, productIdArray);
        if(null!=productExtMap){
            productMobileService.exportProductExtTxt(response, productIds,  productExtMap);
            long endTime =System.currentTimeMillis();
            logger.info("下载数据:{},用时：{} 秒 ",productIds, (endTime - beginTime) / 1000);
        }

    }

}
