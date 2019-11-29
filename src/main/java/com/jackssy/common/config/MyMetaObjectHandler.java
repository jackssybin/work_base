package com.jackssy.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 公共字段自动填充
 * createDate 创建时间
 * createId   创建用户ID
 * updateDate 修改时间
 * updateId   修改用户ID
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object fieldValue = getFieldValByName("createDate", metaObject);
        if(null==fieldValue){
            System.out.println("*******插入操作 满足填充条件*********");
            setFieldValByName("createDate", new Date(), metaObject);
            setFieldValByName("createId", MySysUser.id(), metaObject);
            setFieldValByName("updateDate", new Date(), metaObject);
            setFieldValByName("updateId", MySysUser.id(), metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object fieldValue = getFieldValByName("updateDate", metaObject);
        if(fieldValue == null) {
            System.out.println("*******修改操作 满足填充条件*********");
            setFieldValByName("updateDate", new Date(), metaObject);
            setFieldValByName("updateId", MySysUser.id(), metaObject);
        }

    }
}
