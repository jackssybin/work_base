package com.jackssy.weibo.service;

import com.jackssy.admin.entity.Role;
import com.jackssy.weibo.entity.BzTags;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jackssy
 * @since 2019-11-26
 */
public interface BzTagsService extends IService<BzTags> {

    String getTagsNameByTagsCode(String tagsCode);
}
