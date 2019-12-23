package com.jackssy.weibo.async;

import com.jackssy.admin.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * @author lh
 * @description
 * @date 2019/12/22 0022 22:31
 */
@Component
@Slf4j
public class BzTaskAsync {

    public final static Logger logger = LoggerFactory.getLogger(BzTaskAsync.class);

    @Value("${taskUrl}")
    private String taskUrl;

    @Async
    public void sendUrl(){
        RestTemplate res = new RestTemplate();
        logger.info("taskUrl：{}",taskUrl);
        if(StringUtils.isNoneEmpty(taskUrl)){
            Arrays.stream(taskUrl.split(",")).forEach(url->{
                ResponseEntity<String> response = res.getForEntity(
                        url,String.class);
                logger.info("resp:{}",response.getBody());
            });
        }
    }
}
