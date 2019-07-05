package com.jackssy.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jackssy.admin.entity.BzProductMobile;
import com.jackssy.admin.entity.BzProductShort;
import com.jackssy.admin.mapper.BzProductShortMapper;
import com.jackssy.admin.service.BzProductMobileService;
import com.jackssy.admin.service.BzProductShortService;
import com.jackssy.admin.thread.BatchGetShortUrlThread;
import com.jackssy.admin.thread.BatchInsertThread;
import com.jackssy.admin.thread.JdbcUtilThread;
import com.jackssy.common.util.FileUtil;
import com.jackssy.common.util.ShortenUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackFor = Exception.class)
public class BzProductShortServiceImpl extends
        ServiceImpl<BzProductShortMapper, BzProductShort>
        implements BzProductShortService {

    @Autowired
    private BzProductMobileService productMobileService;

    @Value("${spring.datasource.url}")
    private  String url ;
    @Value("${spring.datasource.username}")
    private  String user ;
    @Value("${spring.datasource.password}")
    private  String password ;

    @Value("${uvstatics.url.prefix}")
    private  String urlPrefix ;

    @Value("${uvstatics.url.default}")
    private String defaultUrl;

    private ConcurrentHashMap<Integer,BzProductShort> productUrlMap=new ConcurrentHashMap<>();

    private final static Logger logger = LoggerFactory.getLogger(BzProductShortServiceImpl.class);

    private static int THREAD_COUNT=40;
    private static int THREAD_SHORT_URL_COUNT=100;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(40);

    @Override
    public void batchTest(BzProductShort bzProductShort) {
        logger.info("异步线程开始执行");
        List<String> phoneList = FileUtil.readPhoneList("");
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);
        int countAll =phoneList.size();
        if(countAll>THREAD_COUNT){
            logger.info("线程池执行");
            batchThreadRun(bzProductShort,  phoneList, countDownLatch);
        }else{
            logger.info("单线程执行");
            singleThreadRun(bzProductShort,  phoneList);
        }
    }

    @Override
    public String getProductUrl(Integer productId) {
        if(productUrlMap.contains(productId)){
            return productUrlMap.get(productId).getProductUrl();
        }
        BzProductShort bzProductShort =getProductByProductId(productId);
        if(null!=bzProductShort){
            productUrlMap.put(productId,bzProductShort);
            return bzProductShort.getProductUrl();
        }
        return defaultUrl;
    }

    @Override
    public String getProductName(Integer productId) {
        if(productUrlMap.contains(productId)){
            return productUrlMap.get(productId).getProdctName();
        }
        BzProductShort bzProductShort =getProductByProductId(productId);
        if(null!=bzProductShort){
            productUrlMap.put(productId,bzProductShort);
            return bzProductShort.getProdctName();
        }

        return "产品名称";
    }
    public BzProductShort getProductByProductId(Integer productId){
        QueryWrapper<BzProductShort> queryWrapper =new QueryWrapper();
        BzProductShort bzProductShort = new BzProductShort();
        bzProductShort.setProductId(productId);
        queryWrapper.setEntity(bzProductShort);
        bzProductShort=this.getOne(queryWrapper);

        return bzProductShort;
    }


    //
    private void batchThreadRun(BzProductShort bzProductShort, List<String> phoneList, CountDownLatch countDownLatch) {
        try {
            long beginTime = System.currentTimeMillis();
            Map<Integer,List<BzProductMobile>> productMap =new HashMap<>();

            long timeStart =System.currentTimeMillis();
            productMap=getProductShortUrlMap(phoneList,bzProductShort.getProductId(),THREAD_COUNT);
//            for(int i = 0;i<phoneList.size() ;i++){
//                productNumber=i%THREAD_COUNT ;
//                if(productMap.containsKey(productNumber)){
//                    list=productMap.get(productNumber);
//                }else{
//                    list=new ArrayList<>();
//                }
//                bb =new BzProductMobile();
//                bb.setGmtCreate(new Date());
//                bb.setPhoneNumber(phoneList.get(i));
//                bb.setProductId(bzProductShort.getProductId());
////				bb.setShortUrl(ShortenUrl.getFixShortUrl(urlPrefix,""+bzProductShort.getProductId(),phoneList.get(i)));
//                logger.info("thread:{} ,current:{},bb:{}",Thread.currentThread().getName(),i,bb);
//                list.add(bb);
//                productMap.put(productNumber,list);
//            }
            long timeEnd =System.currentTimeMillis();
            logger.info("组合数据用时：" + (timeEnd - timeStart) / 1000 + " 秒");
            for (List<BzProductMobile> listdetail : productMap.values()) {
//                threadPool.execute(new BatchInsertThread(countDownLatch, productMobileService,  listdetail));
                threadPool.execute(new JdbcUtilThread(countDownLatch,   listdetail,url,user,password));
            }
            countDownLatch.await();
            long endTime = System.currentTimeMillis();
            logger.info("插入数据用时：" + (endTime - beginTime) / 1000 + " 秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void singleThreadRun(BzProductShort bzProductShortEntity,  List<String> phoneList) {
        CountDownLatch countDownLatch;
        countDownLatch = new CountDownLatch(1);
        List<BzProductMobile> list =new ArrayList<>();
        list=phoneList.stream().map(x->{
            BzProductMobile bb =new BzProductMobile();
            bb.setGmtCreate(new Date());
            bb.setPhoneNumber(x);
            bb.setProductId(bzProductShortEntity.getProductId());
            bb.setShortUrl(ShortenUrl.getFixShortUrl(urlPrefix,""+bzProductShortEntity.getProductId(),x));
            return bb;
        }).collect(Collectors.toList());
        new BatchInsertThread(countDownLatch, productMobileService,  list).run();
    }

    private Map<Integer,List<BzProductMobile>> getProductShortUrlMap(List<String> phoneList,Integer productId,int productCount){
        Map<Integer,List<BzProductMobile>> productMap =new HashMap<>();
        int productNumber=0;
        List<BzProductMobile> list =new ArrayList<>();
        BzProductMobile bb=null;

        for(int i = 0;i<phoneList.size() ;i++){
            productNumber=i%productCount ;
            if(productMap.containsKey(productNumber)){
                list=productMap.get(productNumber);
            }else{
                list=new ArrayList<>();
            }
            bb =new BzProductMobile();
            bb.setGmtCreate(new Date());
            bb.setPhoneNumber(phoneList.get(i));
            bb.setProductId(productId);
//				bb.setShortUrl(ShortenUrl.getFixShortUrl(urlPrefix,""+bzProductShort.getProductId(),phoneList.get(i)));
            logger.info("thread:{} ,current:{},bb:{}",Thread.currentThread().getName(),i,bb);
            list.add(bb);
            productMap.put(productNumber,list);
        }
        CountDownLatch countDownLatch = new CountDownLatch(productCount);
        for (List<BzProductMobile> productMobileList : productMap.values()) {
            threadPool.execute(new BatchGetShortUrlThread(productMobileList, countDownLatch,urlPrefix));
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return productMap;
    }

}
