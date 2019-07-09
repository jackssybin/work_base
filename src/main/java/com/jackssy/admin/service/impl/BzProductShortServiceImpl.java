package com.jackssy.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jackssy.admin.entity.BzProductMobile;
import com.jackssy.admin.entity.BzProductShort;
import com.jackssy.admin.enumtype.ProductStatusEnum;
import com.jackssy.admin.mapper.BzProductShortMapper;
import com.jackssy.admin.service.BzProductMobileService;
import com.jackssy.admin.service.BzProductShortService;
import com.jackssy.admin.thread.BatchGetShortUrlThread;
import com.jackssy.admin.thread.BatchInsertThread;
import com.jackssy.admin.thread.JdbcInsertUtilThread;
import com.jackssy.common.util.FileUtil;
import com.jackssy.common.util.ShortenUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


@Service
public class BzProductShortServiceImpl extends
        ServiceImpl<BzProductShortMapper, BzProductShort>
        implements BzProductShortService {

    private static BlockingQueue<BzProductShort> queue = new ArrayBlockingQueue<BzProductShort>(10);

    private static volatile boolean isRunningStatus =false;

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

    @Value("${uvstatics.url.savePhoneFilePath}")
    private String uploadDir;

    private ConcurrentHashMap<Integer,BzProductShort> productUrlMap=new ConcurrentHashMap<>();

    private final static Logger logger = LoggerFactory.getLogger(BzProductShortServiceImpl.class);

    private static int THREAD_COUNT=40;
    private static int THREAD_SHORT_URL_COUNT=100;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(20);


    @Override
    public void produceQueue(BzProductShort bzProductShort) {
        queue.add(bzProductShort);
    }

    @Override
    public void consumerQueue() {
        while(true){
            logger.info("正从队列获取数据...");
            try {
                    BzProductShort bzProductShort = queue.take();//
                    batchTest(bzProductShort);
                    logger.info("执行完成等待下一个");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void batchTest(BzProductShort bzProductShort) {
        logger.info("异步线程开始执行");
        List<String> phoneList = FileUtil.readPhoneList(uploadDir+bzProductShort.getPhonePath());
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);
        bzProductShort.setStatus(ProductStatusEnum.RUNNING.getCode());
        bzProductShort.setPhoneCount(phoneList.size());
        bzProductShort.setGmtModified(new Date());
        saveOrUpdate(bzProductShort);
        int countAll =phoneList.size();
        if(0==countAll){
            logger.info("数据为空,不能跑批,直接结束");
            bzProductShort.setStatus(ProductStatusEnum.DONE.getCode());
            return;
        }
        if(countAll>THREAD_COUNT){
            logger.info("线程池执行");
            batchThreadRun(bzProductShort,  phoneList, countDownLatch);
        }else{
            logger.info("单线程执行");
            singleThreadRun(bzProductShort,  phoneList);
        }
        bzProductShort.setStatus(ProductStatusEnum.DONE.getCode());
        bzProductShort.setGmtModified(new Date());
        saveOrUpdate(bzProductShort);
    }

    @Override
    public String getProductUrl(Integer productId) {
        if(productUrlMap.containsKey(productId)){
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
        if(productUrlMap.containsKey(productId)){
            return productUrlMap.get(productId).getProdctName();
        }
        BzProductShort bzProductShort =getProductByProductId(productId);
        if(null!=bzProductShort){
            productUrlMap.put(productId,bzProductShort);
            return bzProductShort.getProdctName();
        }

        return "产品名称";
    }

    @Override
    public Integer getProductCount(Integer productId) {
        if(productUrlMap.containsKey(productId)){
            return productUrlMap.get(productId).getPhoneCount();
        }
        BzProductShort bzProductShort =getProductByProductId(productId);
        if(null!=bzProductShort){
            productUrlMap.put(productId,bzProductShort);
            return bzProductShort.getPhoneCount();
        }

        return 0;
    }

    @Override
    public String savePhoneFile( MultipartFile file) {
        String filename="";
        try {
            //文件后缀名
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            //上传文件名
             filename = UUID.randomUUID() + suffix;
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            //服务器端保存的文件对象
            File serverFile = new File(uploadDir + filename);

            if(!serverFile.exists()) {
                //先得到文件的上级目录，并创建上级目录，在创建文件
                serverFile.getParentFile().mkdir();
                try {
                    //创建文件
                    serverFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //将上传的文件写入到服务器端文件内
            file.transferTo(serverFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filename;
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
            long timeEnd =System.currentTimeMillis();
            logger.info("组合数据用时：" + (timeEnd - timeStart) / 1000 + " 秒");
            for (List<BzProductMobile> listdetail : productMap.values()) {
                threadPool.execute(new JdbcInsertUtilThread(countDownLatch,   listdetail,url,user,password));
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
