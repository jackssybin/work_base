package com.jackssy.weibo.async;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author lh
 * @description
 * @date 2019/12/22 0022 22:31
 */
@Component
public class BzTaskAsync {

    @Value("taskUrl")
    private String taskUrl;

    @Async
    public void sendUrl(){
        RestTemplate res = new RestTemplate();
        res.put(taskUrl,null);
    }
}
