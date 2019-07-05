package com.jackssy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class ZJackssybaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZJackssybaseApplication.class, args);
    }
}


