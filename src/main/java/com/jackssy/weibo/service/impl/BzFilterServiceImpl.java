package com.jackssy.weibo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jackssy.weibo.entity.BzFilter;
import com.jackssy.weibo.mapper.BzFilterMapper;
import com.jackssy.weibo.service.BzFilterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lh
 * @description
 * @date 2020/3/29 0029 20:00
 */
@Service
@Slf4j
@Transactional
public class BzFilterServiceImpl extends ServiceImpl<BzFilterMapper, BzFilter> implements BzFilterService {
}
