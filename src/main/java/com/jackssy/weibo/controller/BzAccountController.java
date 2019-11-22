package com.jackssy.weibo.controller;


import com.jackssy.admin.controller.LonginController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jackssy
 * @since 2019-11-22
 */
@RestController
@RequestMapping("/bzAccount")
public class BzAccountController {

    @GetMapping(value = "list")
    public String adminToLogin( Model model) {

        return "account/list";
    }

}

