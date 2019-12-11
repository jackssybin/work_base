package com.jackssy.weibo.entity.dto;

import com.jackssy.weibo.entity.BzTask;
import lombok.Data;

/**
 * @author zhoubin
 * @date 2019/11/27 12:02
 */
@Data
public class BzTaskDto extends BzTask {

    //状态名称
    private String statusName;
    //标签名称
    private String tagsTypeName;

    private String commentTypeName;

    /**
     * 前端转换用
     */
    private String startTimeStr;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTagsTypeName() {
        return tagsTypeName;
    }

    public void setTagsTypeName(String tagsTypeName) {
        this.tagsTypeName = tagsTypeName;
    }

    public String getCommentTypeName() {
        return commentTypeName;
    }

    public void setCommentTypeName(String commentTypeName) {
        this.commentTypeName = commentTypeName;
    }
}
