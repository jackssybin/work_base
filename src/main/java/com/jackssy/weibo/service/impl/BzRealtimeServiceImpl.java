package com.jackssy.weibo.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jackssy.weibo.entity.BzRealtime;
import com.jackssy.weibo.mapper.BzRealtimeMapper;
import com.jackssy.weibo.service.BzRealtimeService;
import org.springframework.stereotype.Service;

/**
 * @author lh
 * @description
 * @date 2020/4/11 0011 18:15
 */
@Service
public class BzRealtimeServiceImpl extends ServiceImpl<BzRealtimeMapper,BzRealtime> implements BzRealtimeService {
}
