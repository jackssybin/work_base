package com.jackssy.weibo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jackssy.admin.controller.BaseController;
import com.jackssy.common.annotation.SysLog;
import com.jackssy.common.base.PageData;
import com.jackssy.common.util.ResponseEntity;
import com.jackssy.weibo.common.Constant;
import com.jackssy.weibo.entity.BzLastreadTask;
import com.jackssy.weibo.entity.BzRegisterTask;
import com.jackssy.weibo.entity.dto.BzRegisterTaskDto;
import com.jackssy.weibo.enums.CardTypeEnums;
import com.jackssy.weibo.enums.StatusNameEnums;
import com.jackssy.weibo.service.BzFilterService;
import com.jackssy.weibo.service.BzLastreadTaskService;
import com.jackssy.weibo.service.BzRegisterTaskService;
import com.jackssy.weibo.service.BzTagsService;
import com.jackssy.weibo.util.WeiBoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jackssy
 * @since 2020-04-18
 */
@Controller
@RequestMapping("/bzRegister")
public class BzRegisterTaskController  extends BaseController {

    @Autowired
    BzTagsService bzTagsService;

    @Autowired
    BzRegisterTaskService bzregister;

    @Autowired
    BzLastreadTaskService bzLastreadTaskService;


    @Autowired
    BzFilterService bzFilterService;






    @GetMapping("list")
    @SysLog("跳转注册任务列表页面")
    public String list(){
        return "weibo/register/list";
    }

    @GetMapping("add")
    @SysLog("跳转注册任务新增页面")
    public String add(ModelMap map){
        map.put("cardTypeList",CardTypeEnums.getEnumdtoList());
        return "weibo/register/add";
    }

    @GetMapping("lastRead")
    @SysLog("昨日阅读任务")
    public String lastRead(ModelMap map){
        QueryWrapper queryWrapper =  new QueryWrapper();
        String uids ="";
        Integer lastreadCount = 0;
        Integer id = 0;
        List<BzLastreadTask> list =bzLastreadTaskService.list(queryWrapper);
        if(!CollectionUtils.isEmpty(list)){
            uids = list.get(0).getUids();
            id = list.get(0).getId();
            lastreadCount = list.get(0).getLastreadCount();
        }
        map.put("id",id);
        map.put("uids",uids);
        map.put("lastreadCount",lastreadCount);

        return "weibo/register/lastRead";
    }


    @GetMapping("edit")
    @SysLog("跳转任务编辑页面")
    public String edit(@RequestParam(value = "id",required = true)String id, ModelMap map)
    {
        BzRegisterTask task = bzregister.getById(id);
        map.put("task",task);
        return "weibo/register/edit";
    }

    @PostMapping("addLastRead")
    @ResponseBody
    @SysLog("保存任务数据")
    public ResponseEntity addLastRead(@RequestBody BzLastreadTask bzLastreadTask){
        bzLastreadTask.setUpdateDate(LocalDateTime.now());
        if(null!=bzLastreadTask.getId()&&bzLastreadTask.getId().intValue()>0){
            bzLastreadTaskService.updateById(bzLastreadTask);
            return ResponseEntity.success("保存任务数据成功");
        }else{
            bzLastreadTask.setCreateDate(LocalDateTime.now());
            boolean flag = bzLastreadTaskService.save(bzLastreadTask);
            if(flag){
                return ResponseEntity.success("保存任务数据成功");
            }else{
                return ResponseEntity.failure("新建任务失败");
            }
        }

    }

    @PostMapping("list")
    @ResponseBody
    public PageData<BzRegisterTaskDto> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                    @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                    ServletRequest request){
        Map map = WebUtils.getParametersStartingWith(request, Constant.REGISTER_PREX);
        PageData<BzRegisterTaskDto> taskPageData = new PageData<>();
        QueryWrapper<BzRegisterTask> taskWrapper = new QueryWrapper<>();
        if(!map.isEmpty()){
            String keys = (String) map.get("key");
            String status = (String) map.get("status");
            if(StringUtils.isNotBlank(status)){
                taskWrapper.eq("status",status);
            }
        }
        taskWrapper.orderByDesc("id");
        IPage<BzRegisterTask> taskPage = bzregister.page(new Page<>(page,limit),taskWrapper);
        taskPageData.setCount(taskPage.getTotal());
        taskPageData.setData(taskPage.getRecords().stream().map(xx ->{
            BzRegisterTaskDto dto =new BzRegisterTaskDto();
            BeanUtils.copyProperties(xx,dto);
            dto.setStatusName(StatusNameEnums.getNameByValue(xx.getStatus()));
            dto.setCartTypeName(CardTypeEnums.getNameByValue(xx.getCardType()));
            if(StringUtils.isNotEmpty(xx.getRegisterTag())){
                dto.setTagsTypeName(bzTagsService.getTagsNameByTagsCode(xx.getRegisterTag()));
            }
            return dto;
        }).collect(Collectors.toList()));
        return taskPageData;
    }

    @PostMapping("add")
    @ResponseBody
    @SysLog("保存任务数据")
    public ResponseEntity add(@RequestBody BzRegisterTaskDto bzRegisterTask){
        if(CardTypeEnums.KA_NONG_CARD_TYPE.getValue().equalsIgnoreCase(bzRegisterTask.getCardType())
        &&StringUtils.isEmpty(bzRegisterTask.getPhoneCountryPre())){
            bzRegisterTask.setRegisterProject(1104);
            bzRegisterTask.setPhoneCountryPre("0044");
            bzRegisterTask.setPhoneCountryName("英国");
        }
        if(StringUtils.isNotBlank(bzRegisterTask.getStartTimeStr())){
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            bzRegisterTask.setStartTime(LocalDateTime.parse(bzRegisterTask.getStartTimeStr(), fmt));
        }else{
            bzRegisterTask.setStatus(1);
            bzRegisterTask.setStartTime(LocalDateTime.now());
        }
        bzRegisterTask.setCreateDate(LocalDateTime.now());
        bzRegisterTask.setUpdateDate(LocalDateTime.now());
        this.setRandomRegion(bzRegisterTask);
        bzRegisterTask.setRegisterTag("9999");
        boolean flag = bzregister.save(bzRegisterTask);
        if(flag){
            return ResponseEntity.success("保存任务数据成功");
        }else{
            return ResponseEntity.failure("新建任务失败");
        }
    }

    public void setRandomRegion(BzRegisterTaskDto bzRegisterTask){
        try {
            Random random = new Random();
            int rn = random.nextInt(WeiBoUtil.regionMap.size());
            int num =0;
            for (String key : WeiBoUtil.regionMap.keySet()) {
                if (rn ==num){
                    bzRegisterTask.setRegionId(key);
                    bzRegisterTask.setRegionName(WeiBoUtil.regionMap.get(key));
                }
                num++;
            }
        } catch (Exception e) {
            bzRegisterTask.setRegionId("360100");
            bzRegisterTask.setRegionName("江西");
        }

    }

    @PostMapping("edit")
    @ResponseBody
    @SysLog("编辑任务数据")
    public ResponseEntity edit(@RequestBody BzRegisterTask bzTask){
        bzregister.updateById(bzTask);
        return ResponseEntity.success("编辑任务数据成功");
    }

    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除任务数据")
    public ResponseEntity delete(@RequestParam(value = "id",required = false)Integer id){
        if(null ==id){
            return ResponseEntity.failure("参数错误");
        }
        BzRegisterTask task = bzregister.getById(id);
        if(task == null){
            return ResponseEntity.failure("任务不存在");
        }
        bzregister.removeById(id);
        return ResponseEntity.success("操作成功");
    }


    @PostMapping("updateStatus")
    @ResponseBody
    @SysLog("修改任务状态")
    public ResponseEntity updateStatus(@RequestParam(value = "id",required = true)Integer id,
                                       @RequestParam(value = "status",required = true) Integer status){
        logger.info("修改任务状态:id:{} status:{}",id,status);
        BzRegisterTask task = bzregister.getById(id);
        if(task == null){
            return ResponseEntity.failure("任务不存在");
        }
        task.setStatus(status);
        boolean flag = bzregister.updateById(task);
        return ResponseEntity.success(flag?"操作成功":"更新失败");
    }




}

