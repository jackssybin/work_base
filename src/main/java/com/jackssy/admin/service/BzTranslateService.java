package com.jackssy.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jackssy.admin.entity.BzTranslate;
import com.jackssy.admin.entity.vo.ProductTranslateVo;

public interface BzTranslateService extends IService<BzTranslate> {

    IPage<ProductTranslateVo> queryTranslateList(Page page);
}
