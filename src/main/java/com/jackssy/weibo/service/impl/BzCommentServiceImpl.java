package com.jackssy.weibo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jackssy.weibo.entity.BzComment;
import com.jackssy.weibo.mapper.BzCommentMapper;
import com.jackssy.weibo.service.BzCommentServce;
import org.springframework.stereotype.Service;

/**
 * Created by lh on 2019/12/12.
 */
@Service
public class BzCommentServiceImpl extends ServiceImpl<BzCommentMapper, BzComment> implements BzCommentServce {
}
