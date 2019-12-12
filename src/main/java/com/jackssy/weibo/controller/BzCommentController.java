package com.jackssy.weibo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.jackssy.admin.controller.BaseController;
import com.jackssy.common.annotation.SysLog;
import com.jackssy.common.base.PageData;
import com.jackssy.common.util.ResponseEntity;
import com.jackssy.weibo.common.Constant;
import com.jackssy.weibo.entity.BzComment;
import com.jackssy.weibo.entity.BzTags;
import com.jackssy.weibo.entity.BzTask;
import com.jackssy.weibo.service.BzCommentServce;
import com.jackssy.weibo.service.BzTagsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lh on 2019/12/9.
 */
@Controller
@RequestMapping("/bzComment")
public class BzCommentController extends BaseController {

    @Autowired
    BzCommentServce bzCommentServce;

    @GetMapping("list")
    @SysLog("跳转到评论列表界面")
    public String list(){return "weibo/comment/list";}

    @PostMapping("list")
    @ResponseBody
    @SysLog("查询评论列表")
    public PageData<BzComment> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                    @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                    ServletRequest request){
        Map map = WebUtils.getParametersStartingWith(request, Constant.COMMENT_PREX);
        PageData<BzComment> bzcData = new PageData<>();
        QueryWrapper<BzComment> bzcWrapper = new QueryWrapper<>();
        if(!map.isEmpty()){
            String keys = (String) map.get("key");
            if(StringUtils.isNotBlank(keys)) {
                bzcWrapper.and(wrapper ->
                        wrapper.like("comment_content", keys));
            }
        }
        bzcWrapper.orderByDesc("update_date");
        IPage<BzComment> commentIPage = bzCommentServce.page(new Page<>(page,limit),bzcWrapper);
        bzcData.setCount(commentIPage.getTotal());
        bzcData.setData(commentIPage.getRecords());
        return bzcData;
    }


    @GetMapping("add")
    @SysLog("跳转评论新增页面")
    public String add(ModelMap map){
        return "weibo/comment/add";
    }

    @PostMapping("add")
    @ResponseBody
    @SysLog("保存分组数据")
    public ResponseEntity add(@RequestBody BzComment bzComment){
        bzComment.setCreateDate(LocalDateTime.now());
        bzComment.setUpdateDate(LocalDateTime.now());
        boolean flag = bzCommentServce.save(bzComment);
        if(flag){
            return ResponseEntity.success("新建评论成功");
        }else{
            return ResponseEntity.failure("新建评论失败");
        }
    }

    /**
     *根据id删除账号
     * @param id 要删除的账号id
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除分组数据")
    public ResponseEntity delete(@RequestParam(value = "id",required = false)Integer id){
        if(null ==id){
            return ResponseEntity.failure("参数错误");
        }
        BzComment bzComment = bzCommentServce.getById(id);
        if(bzComment == null){
            return ResponseEntity.failure("评论不存在");
        }
        bzCommentServce.removeById(id);
        return ResponseEntity.success("操作成功");
    }


    @PostMapping("updateStatus")
    @ResponseBody
    @SysLog("修改评论状态")
    public ResponseEntity updateStatus(@RequestParam(value = "id",required = true)Integer id,@RequestParam(value = "status",required = true) Integer status){
        BzComment bzComment = bzCommentServce.getById(id);
        if(bzComment == null){
            return ResponseEntity.failure("评论不存在");
        }
        bzComment.setStatus(status);
        bzComment.setUpdateDate(LocalDateTime.now());
        boolean flag = bzCommentServce.updateById(bzComment);
        if(!flag){
            return ResponseEntity.failure("操作失败");
        }
        return ResponseEntity.success("操作成功");
    }



}
