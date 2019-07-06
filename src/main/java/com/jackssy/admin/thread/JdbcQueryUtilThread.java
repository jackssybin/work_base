package com.jackssy.admin.thread;


import com.jackssy.admin.entity.BzProductMobile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

/**
 */
public class JdbcQueryUtilThread implements Runnable{
    private final static Logger logger = LoggerFactory.getLogger(JdbcQueryUtilThread.class);

    //"jdbc:mysql://localhost:3306/statics_base?serverTimezone=GMT%2B8&characterEncoding=UTF-8&useSSL=false";
    @Value("${spring.datasource.url}")
    private  String url ="jdbc:mysql://localhost:3306/uvstatics_base?serverTimezone=GMT%2B8&characterEncoding=UTF-8&useSSL=false";
    @Value("${spring.datasource.username}")
    private  String user="root" ;
    @Value("${spring.datasource.password}")
    private  String password="root1234" ;
    private Integer product_id;
    BufferedOutputStream buffer =null;

    List<BzProductMobile> list=new ArrayList<>();
    CountDownLatch latch ;

    public JdbcQueryUtilThread(CountDownLatch latch,
                               Integer product_id,
                               List<BzProductMobile> list,
                               String url, String user, String password) {
        this.list = list;
        this.latch = latch;
        this.product_id =product_id;
        this.url=url;
        this.user =user;
        this.password= password;
    }

    public JdbcQueryUtilThread(CountDownLatch latch,
                               Integer product_id,
                               List<BzProductMobile> list) {
        this.latch = latch;
        this.product_id =product_id;
        this.list=list;
    }

    public JdbcQueryUtilThread(CountDownLatch latch,Integer product_id,BufferedOutputStream buffer) {
        this.latch = latch;
        this.product_id =product_id;
        this.buffer=buffer;
    }

    @Override
    public void run() {
        logger.info("线程执行开始");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            String sql = "select product_id ,phone_number,short_url from bz_product_mobile where product_id='"+product_id+"'";
//            sql = "select product_id ,phone_number,short_url from bz_product_mobile ";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            int num = 0;
            ResultSetMetaData md = rs.getMetaData();
            while (rs.next()) {
                BzProductMobile bzProductMobile =new BzProductMobile();
                bzProductMobile.setProductId(rs.getInt(1));
                bzProductMobile.setPhoneNumber(rs.getString(2));
                bzProductMobile.setShortUrl(rs.getString(3));
                num++;
                if(num%100000==0){
                    logger.info("num:{},bzProductMobile:{}",num,bzProductMobile);
                }
                list.add(bzProductMobile);
                if(null!=buffer)
                buffer.write((bzProductMobile.toString()+"\r\n").getBytes());

            }
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


    public static void main(String[] args) throws InterruptedException {
//        long beginTime = System.currentTimeMillis();
//        CountDownLatch latch = new CountDownLatch(1);
//        new JdbcQueryUtilThread(latch,1021,new ArrayList<>()).run();
//        latch.await();
//        long endTime = System.currentTimeMillis();
//        System.out.println("查询数据用时：" + (endTime - beginTime) / 1000 + " 秒");
        String phoneNum ="15810302515";
        System.out.println(phoneNum.substring(7,11));

    }
}