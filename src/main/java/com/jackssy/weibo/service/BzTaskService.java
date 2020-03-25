package com.jackssy.weibo.service;

import com.jackssy.weibo.entity.BzTask;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jackssy.weibo.entity.dto.BzTaskDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jackssy
 * @since 2019-11-26
 */
public interface BzTaskService extends IService<BzTask> {
    Boolean addTask(BzTaskDto bzTaskDto);

    String getTaskNameByTaskId(String taskId);
}
