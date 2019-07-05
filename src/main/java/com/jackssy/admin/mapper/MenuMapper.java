package com.jackssy.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jackssy.admin.entity.Menu;
import java.util.List;
import java.util.Map;

import com.jackssy.admin.entity.vo.ShowMenuVo;

public interface MenuMapper extends BaseMapper<Menu> {

    List<ShowMenuVo> selectShowMenuByUser(Map<String,Object> map);

    List<Menu> getMenus(Map<String,Object> map);
}