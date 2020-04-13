package com.jackssy.weibo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jackssy.admin.controller.BaseController;
import com.jackssy.common.annotation.SysLog;
import com.jackssy.common.base.PageData;
import com.jackssy.common.config.RedisClient;
import com.jackssy.common.util.ResponseEntity;
import com.jackssy.weibo.async.BzTaskAsync;
import com.jackssy.weibo.common.Constant;
import com.jackssy.weibo.entity.BzFilter;
import com.jackssy.weibo.entity.BzRealtime;
import com.jackssy.weibo.entity.BzTags;
import com.jackssy.weibo.entity.BzTask;
import com.jackssy.weibo.entity.dto.BzTaskDto;
import com.jackssy.weibo.enums.CommentTypeEnums;
import com.jackssy.weibo.enums.StatusNameEnums;
import com.jackssy.weibo.enums.TaskTypeEnums;
import com.jackssy.weibo.service.BzFilterService;
import com.jackssy.weibo.service.BzRealtimeService;
import com.jackssy.weibo.service.BzTagsService;
import com.jackssy.weibo.service.BzTaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jackssy
 * @since 2019-11-26
 */
@Controller
@RequestMapping("/bzSend")
public class BzSendController extends BaseController {




    @Autowired
    BzTagsService bzTagsService;

    @Autowired
    BzTaskService bzTaskService;

    @Autowired
    BzRealtimeService bzRealtimeService;

@Autowired
    BzFilterService bzFilterService;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    BzTaskAsync bzTaskAsync;

    @GetMapping("list")
    @SysLog("跳转微博直发列表页面")
    public String list(){
        return "weibo/send/list";
    }

    @GetMapping("add")
    @SysLog("跳转微博执法新增页面")
    public String add(ModelMap map){
        QueryWrapper<BzTags> tagsQueryWrapper = new QueryWrapper<>();
        List<BzTags> tagsList =bzTagsService.list(tagsQueryWrapper);
        QueryWrapper<BzRealtime> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_date");
        wrapper.eq("type","1");
        List<BzRealtime> realtimeList =bzRealtimeService.list(wrapper);
        map.put("tagsList",tagsList);
        map.put("realtimeList",realtimeList);
        return "weibo/send/add";
    }
    @GetMapping("batchAdd")
    @SysLog("跳转批量任务新增页面")
    public String batchAdd(ModelMap map){
        QueryWrapper<BzTags> tagsQueryWrapper = new QueryWrapper<>();
        List<BzTags> tagsList =bzTagsService.list(tagsQueryWrapper);
        QueryWrapper<BzTask> taskQueryWrapper = new QueryWrapper<>();
        List<Integer> status = new ArrayList<>();
        status.add(StatusNameEnums.STATUS_NAME_UNDO.getValue());
        status.add(StatusNameEnums.STATUS_NAME_DOING.getValue());
        status.add(StatusNameEnums.STATUS_NAME_PAUSE.getValue());
        taskQueryWrapper.in("status",status);
        List<BzTask> taskList = bzTaskService.list(taskQueryWrapper);
        map.put("tagsList",tagsList);
        map.put("taskList",taskList);
        return "weibo/task/batchAdd";
    }

    @GetMapping("edit")
    @SysLog("跳转任务编辑页面")
    public String edit(@RequestParam(value = "id",required = true)String id,ModelMap map)
    {
        BzTask task = bzTaskService.getById(id);
        map.put("bzTask",task);
        QueryWrapper<BzTags> tagsQueryWrapper = new QueryWrapper<>();
        List<BzTags> tagsList =bzTagsService.list(tagsQueryWrapper);
        QueryWrapper<BzRealtime> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_date");
        wrapper.eq("type","1");
        List<BzRealtime> realtimeList =bzRealtimeService.list(wrapper);
        map.put("realtimeList",realtimeList);
        map.put("tagsList",tagsList);
        return "weibo/send/edit";
    }


    @PostMapping("list")
    @ResponseBody
    public PageData<BzTaskDto> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                               @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                               ServletRequest request){
        Map map = WebUtils.getParametersStartingWith(request, Constant.TASK_PREX);
        PageData<BzTaskDto> taskPageData = new PageData<>();
        QueryWrapper<BzTask> taskWrapper = new QueryWrapper<>();
        if(!map.isEmpty()){
            String keys = (String) map.get("key");
            String status = (String) map.get("status");
            if(StringUtils.isNotBlank(keys)) {
                taskWrapper.and(wrapper ->
                        wrapper.like("task_name", keys));
            }
            if(StringUtils.isNotBlank(status)){
                taskWrapper.eq("status",status);
            }
        }
        taskWrapper.eq("send",1);
        taskWrapper.orderByDesc("create_date");
        IPage<BzTask> taskPage = bzTaskService.page(new Page<>(page,limit),taskWrapper);
        taskPageData.setCount(taskPage.getTotal());
        taskPageData.setData(taskPage.getRecords().stream().map(xx ->{
            BzTaskDto dto =new BzTaskDto();
            BeanUtils.copyProperties(xx,dto);
            dto.setStatusName(StatusNameEnums.getNameByValue(xx.getStatus()));
            if(null!=xx.getCommentType()){
                dto.setCommentTypeName(CommentTypeEnums.getNameByValue(xx.getCommentType()));
            }
            dto.setTaskType(TaskTypeEnums.getNameByValue(dto.getTaskType()));
            dto.setTagsTypeName(bzTagsService.getTagsNameByTagsCode(xx.getTagGroup()));
            dto.setPreTaskName(bzTaskService.getTaskNameByTaskId(xx.getPreTaskId()));
            return dto;
        }).collect(Collectors.toList()));
        return taskPageData;
    }

    @PostMapping("add")
    @ResponseBody
    @SysLog("保存任务数据")
    public ResponseEntity add(@RequestBody BzTaskDto bzTaskDto){
        List<String> filterList = bzFilterService.getContainsFilterContent(bzTaskDto.getRemark());
        if(filterList.size()>0){
            String msg = "内容包含关键字";
            for (String keyword : filterList) {
                msg += keyword +",";
            }
            msg+="请修改后提交。";
            return ResponseEntity.failure(msg);
        }
        boolean flag = bzTaskService.addSend(bzTaskDto);
        if(flag){
            return ResponseEntity.success("保存任务数据成功");
        }else{
            return ResponseEntity.failure("新建任务失败");
        }
    }


    @PostMapping("batchAdd")
    @ResponseBody
    @SysLog("保存任务数据")
    public ResponseEntity batchAdd(@RequestBody BzTaskDto bzTaskDto){
        boolean flag = bzTaskService.addSend(bzTaskDto);
        if(flag){
            return ResponseEntity.success("保存任务数据成功");
        }else{
            return ResponseEntity.failure("新建任务失败");
        }
    }


    @PostMapping("edit")
    @ResponseBody
    @SysLog("编辑任务数据")
    public ResponseEntity edit(@RequestBody BzTask bzTask){
        bzTaskService.updateById(bzTask);
        return ResponseEntity.success("编辑任务数据成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除任务数据")
    public ResponseEntity delete(@RequestParam(value = "id",required = false)Integer id){
        if(null ==id){
            return ResponseEntity.failure("参数错误");
        }
        BzTask task = bzTaskService.getById(id);
        if(task == null){
            return ResponseEntity.failure("任务不存在");
        }
        bzTaskService.removeById(id);
        return ResponseEntity.success("操作成功");
    }


    @PostMapping("updateStatus")
    @ResponseBody
    @SysLog("修改任务状态")
    public ResponseEntity updateStatus(@RequestParam(value = "id",required = true)Integer id,
                                       @RequestParam(value = "status",required = true) Integer status){
        logger.info("修改任务状态:id:{} status:{}",id,status);
        BzTask task = bzTaskService.getById(id);
        if(task == null){
            return ResponseEntity.failure("任务不存在");
        }
        task.setStatus(status);
        boolean flag = bzTaskService.updateById(task);
        if(!flag){
            if(task.getStatus() == 1){
                bzTaskAsync.sendUrl();
            }
            return ResponseEntity.failure("操作失败");
        }
        if(StatusNameEnums.STATUS_NAME_DOING.getValue().equals(status)){
            bzTaskAsync.sendUrl();
        }
        return ResponseEntity.success("操作成功");
    }



}

