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
        for(BzProductMobile  bzProductMobile : productMobileList){
            bzProductMobile.setShortUrl(ShortenUrl.getFixShortUrl(urlPrefix,String.valueOf(bzProductMobile.getProductId()),bzProductMobile.getPhoneNumber()));
            logger.info("bzProductMobile:{}",bzProductMobile);
        }
        countDownLatch.countDown();
    }
}
