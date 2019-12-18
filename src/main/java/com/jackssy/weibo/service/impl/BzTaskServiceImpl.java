package com.jackssy.weibo.service.impl;

import com.jackssy.common.util.ResponseEntity;
import com.jackssy.weibo.entity.BzTask;
import com.jackssy.weibo.entity.dto.BzTaskDto;
import com.jackssy.weibo.mapper.BzTaskMapper;
import com.jackssy.weibo.service.BzTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jackssy
 * @since 2019-11-26
 */
@Service
public class BzTaskServiceImpl extends ServiceImpl<BzTaskMapper, BzTask> implements BzTaskService {

    @Override
    public Boolean addTask(BzTaskDto bzTaskDto) {
        BzTask bzTask = new BzTask();
        BeanUtils.copyProperties(bzTaskDto,bzTask);
        bzTask.setCreateDate(LocalDateTime.now());
        bzTask.setUpdateDate(LocalDateTime.now());
        if(StringUtils.isNotBlank(bzTaskDto.getStartTimeStr())){
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            bzTask.setStartTime(LocalDateTime.parse(bzTaskDto.getStartTimeStr(), fmt));
        }else{
            bzTask.setStatus(1);
        }

        if(bzTask.getTaskType().contains("1")){
            bzTask.setComment(1);
        }else{
            bzTask.setCommentContent("");
        }
        if(bzTask.getTaskType().contains("2")){
            bzTask.setRaises(1);
        }
        if(bzTask.getTaskType().contains("3")){
            if(bzTask.getTaskType().contains("1")){
                bzTask.setForwardComment(1);
            }else{
                bzTask.setForward(1);
            }
        }
        if(bzTask.getTaskType().contains("4")){
            bzTask.setFocus(1);
        }



        boolean flag=this.save(bzTask);
        if(flag){
//            String redisKey=Constant.TASK_PREX+bzTask.getId();
//            redisClient.setobj(redisKey,bzTask);
//            logger.info("redis 赋值 ok:{}",redisKey);
//            String val=redisClient.get(redisKey);
//            logger.info("redis 取值:{}",val);

        }
        return flag;
    }
}
