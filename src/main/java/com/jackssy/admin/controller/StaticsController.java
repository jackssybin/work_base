package com.jackssy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jackssy.admin.entity.BzTranslate;
import com.jackssy.admin.service.BzProductMobileService;
import com.jackssy.admin.service.BzProductShortService;
import com.jackssy.admin.service.BzTranslateService;
import com.jackssy.common.util.PhoneUtil;
import com.jackssy.common.util.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

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
//        logger.info("toBrowser phoneNumber:{},productId:{}",phoneNumber,productId);
        map.put("phoneNumber",phoneNumber);
        map.put("productId",productId);
        map.put("productUrl",productShortService.getProductUrl(productId));
        return "admin/blank";
    }

    @PostMapping("saveTranslate")
    @ResponseBody
    public ResponseEntity saveTranslate(@RequestBody BzTranslate bzTranslate){
        logger.info("saveTranslate:{}",bzTranslate);

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







    @PostMapping("refreshQueue")
    @ResponseBody
    public ResponseEntity refreshQueue(){
        logger.info("手动调用队列执行任务");
        productShortService.refreshQueue();
        return new ResponseEntity();
    }

}
