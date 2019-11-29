package com.jackssy.weibo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jackssy.weibo.entity.BzTags;
import com.jackssy.weibo.entity.BzTask;
import com.jackssy.weibo.mapper.BzTagsMapper;
import com.jackssy.weibo.service.BzTagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jackssy
 * @since 2019-11-26
 */
@Service
public class BzTagsServiceImpl extends ServiceImpl<BzTagsMapper, BzTags> implements BzTagsService {

    @Override
    public String getTagsNameByTagsCode(String tagCodes) {
        if(StringUtils.isNotEmpty(tagCodes)){
            QueryWrapper< BzTags > tagWrapper = new QueryWrapper<>();
            tagWrapper.in("tag_code",tagCodes.split(","));
            List<BzTags> tags =this.list(tagWrapper);

            return  tags.stream().map(BzTags::getTagName) .collect(Collectors.joining(","));
        }
        return "";
    }
}
