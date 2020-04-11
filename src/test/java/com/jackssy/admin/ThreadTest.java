package com.jackssy.admin;

import org.junit.Test;

public class ThreadTest {


    @Test
    public void test(){
        Thread t = new Thread(){
            @Override
            public void run() {
                pong();
            }
        };

        t.start();
        System.out.println("ping");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void pong(){
        System.out.println("pong");
    }
}
