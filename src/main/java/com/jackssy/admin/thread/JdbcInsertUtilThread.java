package com.jackssy.admin.thread;


import com.jackssy.admin.entity.BzProductMobile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

/**
 */
//@Component
public class JdbcInsertUtilThread implements Runnable{
    private final static Logger logger = LoggerFactory.getLogger(JdbcInsertUtilThread.class);

    //"jdbc:mysql://localhost:3306/statics_base?serverTimezone=GMT%2B8&characterEncoding=UTF-8&useSSL=false";
    @Value("${spring.datasource.url}")
    private  String url ;
    @Value("${spring.datasource.username}")
    private  String user ;
    @Value("${spring.datasource.password}")
    private  String password ;

    private static ExecutorService threadPool = Executors.newFixedThreadPool(20);

    private static final LongAdder longAdder = new LongAdder();

    public static int singBatchNum=10000;
    public static int threadNum=100;
    public static Object lock=new Object();

    List<BzProductMobile> list=new ArrayList<>();
    CountDownLatch latch ;

    public JdbcInsertUtilThread(CountDownLatch latch,
                                List<BzProductMobile> list, String url, String user, String password) {
        this.list = list;
        this.latch = latch;
        this.url=url;
        this.user =user;
        this.password= password;
    }

    @Override
    public void run() {
        logger.info("线程执行开始");
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            String sql = "";
            sql ="INSERT INTO bz_product_mobile (product_id, phone_number, short_url, gmt_create) VALUES (?,?,?,?)";

            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);

            logger.info("待插入数量:{}",list.size());
            if(list.size()>singBatchNum){
                for(int i =0 ;i<list.size() ;i++){

                    ps.setLong(1,list.get(i).getProductId());
                    ps.setString(2, list.get(i).getPhoneNumber());
//                ps.setString(3,ShortenUrl.getFixShortUrl(""+random.nextInt(1000)));
                    ps.setString(3,list.get(i).getShortUrl());
                    ps.setDate(4,new java.sql.Date(System.currentTimeMillis()));
                    ps.addBatch();

                    if(i+1%singBatchNum==0){
                        ps.executeBatch();
                        conn.commit();
                    }
                }
            }else{
                for (int j = 0; j < list.size(); j++) {
                    ps.setLong(1,list.get(j).getProductId());
                    ps.setString(2, list.get(j).getPhoneNumber());
//                ps.setString(3,ShortenUrl.getFixShortUrl(""+random.nextInt(1000)));
                    ps.setString(3,list.get(j).getShortUrl());
                    ps.setDate(4,new java.sql.Date(System.currentTimeMillis()));
                    ps.addBatch();
                }
            }
            ps.executeBatch();
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            latch.countDown();
        }
    }

    public static  Long getLongPrimary(){
        synchronized(lock){
            longAdder.increment();
            return longAdder.longValue();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long beginTime = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            threadPool.execute(() -> {
                try {
//                    JdbcInsertUtilThread.executeBatch();
                } catch (Exception e) {
                    System.out.println("插入数据异常");
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        long endTime = System.currentTimeMillis();
        System.out.println("插入"+singBatchNum*threadNum+"数据用时：" + (endTime - beginTime) / 1000 + " 秒");
        threadPool.shutdown();
    }
}