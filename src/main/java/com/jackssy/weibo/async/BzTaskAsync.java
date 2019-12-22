package com.jackssy.weibo.async;

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

    @Async
    public void sendUrl(){
        RestTemplate res = new RestTemplate();
        res.put("http://47.93.233.127/api/start_task/,http://127.0.0.1/api/start_task/",null);
    }
}
