package com.jackssy.admin.thread;
import com.jackssy.admin.entity.BzProductMobile;
import com.jackssy.admin.service.impl.BzProductShortServiceImpl;
import com.jackssy.common.util.ShortenUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class BatchGetShortUrlThread implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(BzProductShortServiceImpl.class);
    List<BzProductMobile> productMobileList;
    CountDownLatch countDownLatch;
    String urlPrefix;

    public BatchGetShortUrlThread(List<BzProductMobile> productMobileList, CountDownLatch countDownLatch,String urlPrefix) {
        this.productMobileList = productMobileList;
        this.countDownLatch = countDownLatch;
        this.urlPrefix =urlPrefix;
    }

    @Override
    public void run() {
        int i=0;
        for(BzProductMobile  bzProductMobile : productMobileList){
            bzProductMobile.setShortUrl(ShortenUrl.getFixShortUrl(urlPrefix,String.valueOf(bzProductMobile.getProductId()),bzProductMobile.getPhoneNumber()));
            if(i%10000==0){
                logger.info("i:{}bzProductMobile:{}",i,bzProductMobile);
            }
            i++;

        }
        countDownLatch.countDown();
    }
}
