package com.jackssy.weibo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jackssy.common.annotation.SysLog;
import com.jackssy.common.base.PageData;
import com.jackssy.common.util.ResponseEntity;
import com.jackssy.weibo.common.Constant;
import com.jackssy.weibo.entity.BzAccount;
import com.jackssy.weibo.entity.BzTags;
import com.jackssy.weibo.service.BzTagsService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import javax.servlet.ServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jackssy
 * @since 2019-11-26
 */
@Controller
@RequestMapping("/bzTags")
public class BzTagsController {

    @Autowired
    private BzTagsService bzTagsService;

    @GetMapping("list")
    @SysLog("跳转到分组列表界面")
    public String list(){return "weibo/tag/list";}

    @PostMapping("list")
    @ResponseBody
    @SysLog("查询分组列表")
    public PageData<BzTags> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                 @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                 ServletRequest request){
        Map map = WebUtils.getParametersStartingWith(request, Constant.TAG_PREX);
        PageData<BzTags> bzTagsData = new PageData<>();
        QueryWrapper<BzTags> bzTagsWrapper = new QueryWrapper<>();
        if(!map.isEmpty()){
            String keys = (String) map.get("key");
            if(StringUtils.isNotBlank(keys)) {
                bzTagsWrapper.and(wrapper ->
                        wrapper.like("tag_name", keys))
                .or(wrapper -> wrapper.like("tag_code",keys));
            }
        }
        IPage<BzTags> tagsIPage = bzTagsService.page(new Page<>(page,limit),bzTagsWrapper);
        bzTagsData.setCount(tagsIPage.getTotal());
        bzTagsData.setData(tagsIPage.getRecords());
        return bzTagsData;
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
        BzTags task = bzTagsService.getById(id);
        if(task == null){
            return ResponseEntity.failure("分组不存在");
        }
        bzTagsService.removeById(id);
        return ResponseEntity.success("操作成功");
    }

}

