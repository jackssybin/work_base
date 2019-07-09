package com.jackssy.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jackssy.admin.entity.BzTranslate;
import com.jackssy.admin.entity.vo.ProductTranslateVo;

public interface BzTranslateMapper extends BaseMapper<BzTranslate> {

    IPage<ProductTranslateVo> queryTranslateList(Page page);
}
