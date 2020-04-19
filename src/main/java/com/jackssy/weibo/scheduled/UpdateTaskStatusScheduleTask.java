package com.jackssy.weibo.scheduled;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jackssy.weibo.async.BzTaskAsync;
import com.jackssy.weibo.entity.BzRegisterTask;
import com.jackssy.weibo.entity.BzTask;
import com.jackssy.weibo.enums.StatusNameEnums;
import com.jackssy.weibo.service.BzRegisterTaskService;
import com.jackssy.weibo.service.BzTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by lh on 2019/12/16.
 */
@Configuration
@EnableScheduling
public class UpdateTaskStatusScheduleTask {
    Logger log  = LoggerFactory.getLogger(UpdateTaskStatusScheduleTask.class);

    @Autowired
    BzTaskService bzTaskService;

    @Autowired
    BzRegisterTaskService bzregister;

    @Autowired
    BzTaskAsync bzTaskAsync;

    //@Scheduled(fixedRate = 1000)
    @Scheduled(cron = "55 * * * * *")
    private void ConfigureTask(){
        LocalDateTime time = LocalDateTime.now();
        log.info("轮询启动任务时间：{}", time);
        try {
            updateTask(time);
            updateRegisterTask(time);

        }catch (Exception e){
            log.error("轮询启动任务时间,异常",e);
        }
    }

    private void updateTask(LocalDateTime time) {
        QueryWrapper<BzTask> taskWrapper = new QueryWrapper<>();
        taskWrapper.and(wrapper ->
                wrapper.lt("start_time",time ).like("status", StatusNameEnums.STATUS_NAME_UNDO.getValue())
        );
        List<BzTask> taskList = (List<BzTask>) this.bzTaskService.list(taskWrapper);
        taskList.forEach(item -> {
            item.setStatus(StatusNameEnums.STATUS_NAME_DOING.getValue());
            this.bzTaskService.updateById(item);
            this.bzTaskAsync.sendUrl();
        });
        log.info("轮询启动任务时间,处理条数：{}",taskList.size());
    }

    private void updateRegisterTask(LocalDateTime time) {
        QueryWrapper<BzRegisterTask> registerTaskQueryWrapper = new QueryWrapper<>();
        registerTaskQueryWrapper.and(wrapper ->
                wrapper.lt("start_time",time ).like("status", StatusNameEnums.STATUS_NAME_UNDO.getValue())
        );
        List<BzRegisterTask> registerList = (List<BzRegisterTask>) this.bzregister.list(registerTaskQueryWrapper);
        registerList.forEach(item -> {
            item.setStatus(StatusNameEnums.STATUS_NAME_DOING.getValue());
            this.bzregister.updateById(item);
        });
        log.info("轮询启动任务时间,处理条数：{}",registerList.size());
    }

    ;
}
