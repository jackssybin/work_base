package com.jackssy.weibo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lh
 * @description
 * @date 2020/3/29 0029 19:56
 */
@TableName("bz_filter")
@Data
public class BzFilter extends Model<BzFilter> {
    @TableId
    private String id;

    private String filterContent;

    private Integer filterOrder;

    private Integer  isUse;

    private String  remark;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
