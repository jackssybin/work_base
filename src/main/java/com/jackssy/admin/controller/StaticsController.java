package com.jackssy.admin.controller;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jackssy.admin.entity.BzProductMobile;
import com.jackssy.admin.entity.BzTranslate;
import com.jackssy.admin.excel.config.ExcelUtil;
import com.jackssy.admin.excel.controller.BzTranslateExportInfo;
import com.jackssy.admin.service.BzProductMobileService;
import com.jackssy.admin.service.BzProductShortService;
import com.jackssy.admin.service.BzTranslateService;

import com.jackssy.common.util.DESUtil;
import com.jackssy.common.util.PhoneUtil;
import com.jackssy.common.util.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("uv")
public class StaticsController {

    private final static Logger logger = LoggerFactory.getLogger(StaticsController.class);

    @Autowired
    private BzProductShortService productShortService;

    @Autowired
    private BzTranslateService translateService;

    @Autowired
    private BzProductMobileService productMobileService ;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value="bs/{phoneNum}/{productId}",method= RequestMethod.GET)
     public String sayHello(@PathVariable("phoneNum") String  phoneNum, @PathVariable("productId") String productId){
        logger.info("phoneNum:{},productId:{}",phoneNum,productId);
        return "redirect:http://www.baidu.com";
    }

    @RequestMapping(value="/{phoneNumber}/{productId}",method= RequestMethod.GET)
    public String toBrowser(ModelMap map,
                            @PathVariable("phoneNumber") String  phoneNumber,
                            @PathVariable("productId") Integer productId){
        phoneNumber= PhoneUtil.decryptPhone(phoneNumber);
        logger.info("toBrowser phoneNumber:{},productId:{}",phoneNumber,productId);
        map.put("phoneNumber",phoneNumber);
        map.put("productId",productId);
        map.put("productUrl",productShortService.getProductUrl(productId));
        return "admin/blank";
    }

    @PostMapping("saveTranslate")
    @ResponseBody
    public ResponseEntity saveTranslate(@RequestBody BzTranslate bzTranslate){

        QueryWrapper<BzTranslate> queryWrapper = new QueryWrapper<>();
        BzTranslate bzTemp = new BzTranslate();
        bzTemp.setPhoneNumber(bzTranslate.getPhoneNumber());
        bzTemp.setProductId(bzTranslate.getProductId());
        queryWrapper.setEntity(bzTemp);
        bzTemp=translateService.getOne(queryWrapper);
        if(bzTemp==null){
            bzTranslate.setGmtCreate(new Date());
            bzTranslate.setClickNumber(1);
            translateService.save(bzTranslate);
        }else{
            bzTemp.setClickNumber(bzTemp.getClickNumber()+1);
            translateService.update(bzTemp,queryWrapper);
        }
        return ResponseEntity.success("操作成功");
    }



    @RequestMapping(value = "writeTranslateExcel", method = RequestMethod.GET)
    public void writeTranslateExcel(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String productId=request.getParameter("productId");
        if(StringUtils.isEmpty(productId)){
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
        if(StringUtils.isEmpty(productIds)){
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

//    @RequestMapping(value = "testTranslate", method = RequestMethod.GET)
//    public void testTranslate(HttpServletRequest request,HttpServletResponse response) throws Exception{
//        QueryWrapper<BzProductMobile> queryWrapper = new QueryWrapper<>();
//        BzProductMobile bzProductMobile = new BzProductMobile();
//        queryWrapper.setEntity(bzProductMobile);
//
//        IPage<BzProductMobile> userPage = productMobileService.page(new Page<>(1,1000),queryWrapper);
//        userPage.getRecords().stream().forEach(xx->{
//            logger.info(xx.getShortUrl());
//            org.springframework.http.ResponseEntity<String> responseEntity= restTemplate.getForEntity(xx.getShortUrl(),String.class);
//            logger.info(responseEntity.getBody());
//        });
//    }






}
