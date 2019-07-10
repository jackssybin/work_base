package com.jackssy.admin.commanderrun;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jackssy.admin.entity.BzProductShort;
import com.jackssy.admin.enumtype.ProductStatusEnum;
import com.jackssy.admin.service.BzProductShortService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);

    @Autowired
    private BzProductShortService productShortService;

    @Override
    public void run(String... args) throws Exception {
            logger.info("启动线程");
        productShortService.refreshQueue();
    }
}
