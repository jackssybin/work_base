package com.jackssy.weibo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.jackssy.admin.controller.BaseController;
import com.jackssy.common.annotation.SysLog;
import com.jackssy.common.base.PageData;
import com.jackssy.weibo.entity.BzTags;
import com.jackssy.weibo.service.BzTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

/**
 * Created by lh on 2019/12/9.
 */
@Controller
@RequestMapping("/bzComment")
public class BzCommentController extends BaseController {

    @Autowired
    private BzTagsService bzTagsService;

    @GetMapping("list")
    @SysLog("跳转到评论列表界面")
    public String list(){return "weibo/comment/list";}

    @PostMapping("list")
    @ResponseBody
    @SysLog("查询分组列表")
    public PageData<BzTags> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                 @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                 ServletRequest request){
        PageData<BzTags> bzTagsData = new PageData<>();
        QueryWrapper<BzTags> bzTagsWrapper = new QueryWrapper<>();
        IPage<BzTags> tagsIPage = bzTagsService.page(new Page<>(page,limit),bzTagsWrapper);
        bzTagsData.setCount(tagsIPage.getTotal());
        bzTagsData.setData(tagsIPage.getRecords());
        return bzTagsData;
    }
}
