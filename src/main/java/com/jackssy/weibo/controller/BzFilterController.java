package com.jackssy.weibo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jackssy.admin.controller.BaseController;
import com.jackssy.common.annotation.SysLog;
import com.jackssy.common.base.PageData;
import com.jackssy.common.util.ResponseEntity;
import com.jackssy.weibo.common.Constant;
import com.jackssy.weibo.entity.BzFilter;
import com.jackssy.weibo.entity.BzTags;
import com.jackssy.weibo.entity.BzTask;
import com.jackssy.weibo.enums.StatusNameEnums;
import com.jackssy.weibo.service.BzFilterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lh
 * @description
 * @date 2020/3/29 0029 19:55
 */
@Controller
@RequestMapping("/bzFilter")
public class BzFilterController extends BaseController {

    @Autowired
    BzFilterService bzFilterService;

    @GetMapping("list")
    @SysLog("跳转关键词列表页面")
    public String list(){
        return "weibo/filter/list";
    }

    @PostMapping("list")
    @ResponseBody
    @SysLog("查询关键词列表")
    public PageData<BzFilter> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                 @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                 ServletRequest request){
        Map map = WebUtils.getParametersStartingWith(request, Constant.TAG_PREX);
        PageData<BzFilter> BzFilterData = new PageData<>();
        QueryWrapper<BzFilter> bzFilterWrapper = new QueryWrapper<>();
        if(!map.isEmpty()){
            String keys = (String) map.get("key");
            if(StringUtils.isNotBlank(keys)) {
                bzFilterWrapper.like("filter_content", keys);
            }
        }
        bzFilterWrapper.orderByDesc("create_date");
        IPage<BzFilter> filterIPage = bzFilterService.page(new Page<>(page,limit),bzFilterWrapper);
        BzFilterData.setCount(filterIPage.getTotal());
        BzFilterData.setData(filterIPage.getRecords());
        return BzFilterData;
    }


    @GetMapping("add")
    @SysLog("跳转关键词新增页面")
    public String add(ModelMap map){
        return "weibo/filter/add";
    }

    @PostMapping("add")
    @ResponseBody
    @SysLog("保存关键词数据")
    public ResponseEntity add(@RequestBody BzFilter bzFilter){

        QueryWrapper<BzFilter> wrapper = new QueryWrapper<>();
        wrapper.eq("filter_content",bzFilter.getFilterContent());
        BzFilter exist = bzFilterService.getOne(wrapper);
        if(exist!=null){
            return ResponseEntity.failure("关键词已存在");
        }
        bzFilter.setId();
        bzFilter.setIsUse(1);
        bzFilter.setCreateDate(LocalDateTime.now());
        bzFilter.setUpdateDate(LocalDateTime.now());
        boolean flag = bzFilterService.save(bzFilter);
        if(flag){
            return ResponseEntity.success("新建关键词成功");
        }else{
            return ResponseEntity.failure("新建关键词失败");
        }
    }



    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除关键字数据")
    public ResponseEntity delete(@RequestParam(value = "id",required = false)Integer id){
        if(null ==id){
            return ResponseEntity.failure("参数错误");
        }
        BzFilter filter = bzFilterService.getById(id);
        if(filter == null){
            return ResponseEntity.failure("数据不存在");
        }
        bzFilterService.removeById(id);
        return ResponseEntity.success("操作成功");
    }

    @PostMapping("updateStatus")
    @ResponseBody
    @SysLog("修改关键词启用状态")
    public ResponseEntity updateStatus(@RequestParam(value = "id",required = true)Integer id,
                                       @RequestParam(value = "isUse",required = true) Integer isUse){
        logger.info("修改关键词启用状态:id:{} isUse:{}",id,isUse);
        BzFilter filter = bzFilterService.getById(id);
        if(filter == null){
            return ResponseEntity.failure("数据不存在");
        }
        filter.setIsUse(isUse);
        filter.setUpdateDate(LocalDateTime.now());
        bzFilterService.updateById(filter);
        return ResponseEntity.success("操作成功");
    }

}
