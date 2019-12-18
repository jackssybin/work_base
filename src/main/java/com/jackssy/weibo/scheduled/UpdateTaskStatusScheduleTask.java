package com.jackssy.weibo.scheduled;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jackssy.weibo.entity.BzTags;
import com.jackssy.weibo.entity.BzTask;
import com.jackssy.weibo.service.BzTaskService;
import com.sun.javafx.collections.MappingChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Wrapper;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lh on 2019/12/16.
 */
@Configuration
@EnableScheduling
public class UpdateTaskStatusScheduleTask {
    Logger log  = LoggerFactory.getLogger(UpdateTaskStatusScheduleTask.class);

    @Autowired
    BzTaskService bzTaskService;

    @Scheduled(fixedRate = 1000)
    private void ConfigureTask(){
        LocalDateTime time = LocalDateTime.now();
        log.info("轮询启动任务时间：{}", time);
        try {
            QueryWrapper<BzTask> taskWrapper = new QueryWrapper<>();
            taskWrapper.and(wrapper ->
                    wrapper.lt("start_time",time ).like("status",0)
            );
            List<BzTask> taskList = (List<BzTask>) this.bzTaskService.list(taskWrapper);
            taskList.forEach(item -> {
                item.setStatus(1);
                this.bzTaskService.updateById(item);
            });
            log.info("轮询启动任务时间,处理条数：{}",taskList.size());
        }catch (Exception e){
            log.error("轮询启动任务时间,异常",e);
        }
    };
}
