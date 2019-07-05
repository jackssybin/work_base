package com.jackssy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jackssy.admin.entity.BzTranslate;
import com.jackssy.admin.excel.config.ExcelUtil;
import com.jackssy.admin.excel.controller.BzTranslateExportInfo;
import com.jackssy.admin.service.BzProductShortService;
import com.jackssy.admin.service.BzTranslateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("uv")
public class StaticsController {

    private final static Logger logger = LoggerFactory.getLogger(StaticsController.class);

    @Autowired
    private BzProductShortService productShortService;

    @Autowired
    private BzTranslateService bzTranslateService;

    @RequestMapping(value="bs/{phoneNum}/{productId}",method= RequestMethod.GET)
     public String sayHello(@PathVariable("phoneNum") String  phoneNum, @PathVariable("productId") String productId){
        logger.info("phoneNum:{},productId:{}",phoneNum,productId);
        return "redirect:http://www.baidu.com";
    }

    @RequestMapping(value="/{phoneNum}/{productId}",method= RequestMethod.GET)
    public String toBrowser(ModelMap map,
                            @PathVariable("phoneNum") String  phoneNum,
                            @PathVariable("productId") Integer productId){
        logger.info("phoneNum:{},productId:{}",phoneNum,productId);
        map.put("phoneNum",phoneNum);
        map.put("productId",productId);
        map.put("productUrl",productShortService.getProductUrl(productId));
        return "admin/blank";
    }

    @RequestMapping(value = "writeProductTranslate", method = RequestMethod.GET)
    public void writeProductTranslate(HttpServletResponse response) throws IOException {
        QueryWrapper<BzTranslate> queryWrapper = new QueryWrapper<>();
        BzTranslate bzTranslate = new BzTranslate();
        queryWrapper.setEntity(bzTranslate);

        List<BzTranslate> bzTranslateList = bzTranslateService.list(queryWrapper);
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
}
