package com.jackssy.weibo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jackssy.weibo.entity.BzFilter;

import java.util.List;

/**
 * @author lh
 * @description
 * @date 2020/3/29 0029 19:56
 */
public interface BzFilterService extends IService<BzFilter> {
    List<BzFilter> listIsUse();

    List<String> getContainsFilterContent(String text);
}
