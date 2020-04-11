package com.jackssy.weibo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lh
 * @description
 * @date 2020/4/11 0011 18:11
 */
@TableName("bz_realtime")
@Data
public class BzRealtime extends Model<BzRealtime> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String link;

    private String type;

    private String realDateHour;

    private Integer maxTag;

    private LocalDateTime updateDate;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;


    @Override
    protected Serializable pkVal() {
        return null;
    }
}
