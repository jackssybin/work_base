package com.jackssy.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jackssy.admin.controller.StaticsController;
import com.jackssy.admin.entity.BzProductMobile;
import com.jackssy.admin.enumtype.ProductCommon;
import com.jackssy.admin.mapper.BzProductMobileMapper;
import com.jackssy.admin.service.BzProductMobileService;
import com.jackssy.admin.thread.JdbcQueryUtilThread;
import com.jackssy.common.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Service
@Transactional(rollbackFor = Exception.class)
public class BzProductMobileServiceImpl extends
        ServiceImpl<BzProductMobileMapper, BzProductMobile>
        implements BzProductMobileService {
    private final static Logger logger = LoggerFactory.getLogger(BzProductMobileServiceImpl.class);

    @Value("${uvstatics.url.exportSmsName:短信模板}")
    private String exportSmsName;

    @Value("${spring.datasource.url}")
    private  String url ;
    @Value("${spring.datasource.username}")
    private  String user ;
    @Value("${spring.datasource.password}")
    private  String password;

    @Override
    public void exportProductExtTxt(HttpServletResponse response, String productIds, Map<String, String> productExtMap) {
        String fileName = exportSmsName+ DateUtils.getNowStr() + ".txt";
        response.setContentType("text/plain");
        try {
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ServletOutputStream outputStream = null;
        BufferedOutputStream buffer = null;
        try {
            outputStream = response.getOutputStream();
            buffer = new BufferedOutputStream(outputStream);
            logger.info("输出最终文本txt结果");
            String wxStr="";
            for(String phoneNum:productExtMap.keySet()){
                wxStr=phoneNum+ProductCommon.WRITE_TXT_SPLIT_STR+productExtMap.get(phoneNum)+"\r\n";
                buffer.write(wxStr.getBytes("UTF-8"));
            }
            productExtMap.clear();
            buffer.flush();
            buffer.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, String> getProductExtMap(HttpServletResponse response, Integer[] productIdArray)throws IOException {
        Map<String, String> productExtMap =new HashMap<>();
        CountDownLatch latch = new CountDownLatch(productIdArray.length);
        Map<Integer, List<BzProductMobile>> productMap= new HashMap<>();
        for(Integer productId:productIdArray){
            productMap.put(productId,new ArrayList<>());
            new JdbcQueryUtilThread(latch,productId,productMap.get(productId),url,user,password).run();
        }
        try {
            latch.await();

            for(Integer productId:productIdArray){
                logger.info("productId,list:{}",productId,productMap.get(productId).size());
                productMap.get(productId).stream().forEach(xx ->{
                    String val ="";
                    String back4Number="";
                    if(productExtMap.containsKey(xx.getPhoneNumber())){
                        val=productExtMap.get(xx.getPhoneNumber())+ProductCommon.WRITE_TXT_SPLIT_STR+xx.getShortUrl();
                    }else{
                        back4Number=xx.getPhoneNumber().substring(7,11);
                        val=back4Number+ ProductCommon.WRITE_TXT_SPLIT_STR+xx.getShortUrl();
                    }
                    productExtMap.put(xx.getPhoneNumber(),val);
                });
            }
            productMap.clear();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return productExtMap;
    }
}
