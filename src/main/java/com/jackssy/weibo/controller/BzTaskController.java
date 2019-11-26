package com.jackssy.weibo.controller;


import com.jackssy.common.annotation.SysLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jackssy
 * @since 2019-11-26
 */
@Controller
@RequestMapping("/bzTask")
public class BzTaskController {

    @GetMapping("list")
    @SysLog("跳转任务列表页面")
    public String list(){
        return "weibo/task/list";
    }

    @GetMapping("add")
    @SysLog("跳转任务新增页面")
    public String add(){
        return "weibo/task/add";
    }

    @GetMapping("edit")
    @SysLog("跳转任务编辑页面")
    public String edit(){
        return "weibo/task/edit";
    }

}

