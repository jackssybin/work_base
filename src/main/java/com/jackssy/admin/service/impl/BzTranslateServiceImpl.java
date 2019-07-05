package com.jackssy.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jackssy.admin.entity.BzTranslate;
import com.jackssy.admin.mapper.BzTranslateMapper;
import com.jackssy.admin.service.BzTranslateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class BzTranslateServiceImpl extends
            ServiceImpl<BzTranslateMapper, BzTranslate>
            implements BzTranslateService {

}
