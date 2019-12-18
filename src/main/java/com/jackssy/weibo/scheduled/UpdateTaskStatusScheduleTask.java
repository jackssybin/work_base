package com.jackssy.weibo.scheduled;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jackssy.weibo.entity.BzTask;
import com.jackssy.weibo.enums.StatusNameEnums;
import com.jackssy.weibo.service.BzTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by lh on 2019/12/16.
 */
@Component
public class UpdateTaskStatusScheduleTask {

    @Autowired
    private BzTaskService bzTaskService;
//    @Scheduled(fixedRate = 2000)
    @Scheduled(cron = "55 * * * * *")
    public void test() {
        System.out.println("[ 当前时间:" + new Date() + "]" );
        QueryWrapper<BzTask> taskWrapper = new QueryWrapper<>();
        taskWrapper.eq("status", StatusNameEnums.STATUS_NAME_UNDO.getValue());
        List<BzTask> taskList=bzTaskService.list(taskWrapper);
        taskList.stream().filter(bzTask -> null!=bzTask.getStartTime()&&bzTask.getStartTime().isBefore(LocalDateTime.now()))
                .forEach(bzTask -> {
                    bzTask.setStatus(StatusNameEnums.STATUS_NAME_DOING.getValue());
                    bzTask.setUpdateDate(LocalDateTime.now());
                    this.bzTaskService.updateById(bzTask);
                });



    }
}
