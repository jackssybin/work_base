package com.jackssy.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jackssy.admin.entity.BzProductMobile;
import com.jackssy.admin.mapper.BzProductMobileMapper;
import com.jackssy.admin.service.BzProductMobileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class BzProductMobileServiceImpl extends
        ServiceImpl<BzProductMobileMapper, BzProductMobile>
        implements BzProductMobileService {


}
