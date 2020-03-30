package com.jackssy.weibo.controller;

import com.jackssy.admin.controller.BaseController;
import com.jackssy.common.annotation.SysLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lh
 * @description
 * @date 2020/3/29 0029 19:55
 */
@Controller
@RequestMapping("/bzFilter")
public class BzFilterController extends BaseController {

    @GetMapping("list")
    @SysLog("跳转关键词列表页面")
    public String list(){
        return "weibo/filter/list";
    }
}
