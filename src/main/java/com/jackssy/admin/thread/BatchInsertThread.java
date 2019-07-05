package com.jackssy.admin.thread;

import com.jackssy.admin.entity.BzProductMobile;
import com.jackssy.admin.service.BzProductMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class BatchInsertThread implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(BatchInsertThread.class);

    CountDownLatch countDownLatch ;

    BzProductMobileService productMobileService;

    List<BzProductMobile> list=new ArrayList<>();

    public BatchInsertThread(CountDownLatch countDownLatch,BzProductMobileService productMobileService,
                             List<BzProductMobile> list) {
        this.countDownLatch = countDownLatch;
        this.list = list;
        this.productMobileService=productMobileService;
    }

    @Override
    public void run() {
        logger.info("线程开始:{}",Thread.currentThread().getName());
        productMobileService.saveBatch(list,5000);
        countDownLatch.countDown();

        logger.info("线程执行完成:{}",Thread.currentThread().getName());

    }
}
