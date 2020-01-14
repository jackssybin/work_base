package com.jackssy.admin.controller;

import com.jackssy.common.annotation.SysLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ping")
public class PingController {


    @RequestMapping("pong")
    @SysLog("测试接口")
    public String pingpong(){
        return "ok";
    }

}
