package com.jackssy.weibo.entity.dto;

import com.jackssy.weibo.entity.BzRegisterTask;

public class BzRegisterTaskDto extends BzRegisterTask {


    //状态名称
    private String statusName;
    //标签名称
    private String tagsTypeName;
    //卡商类型
    private String cartTypeName;

    private String startTimeStr;

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

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

    public String getCartTypeName() {
        return cartTypeName;
    }

    public void setCartTypeName(String cartTypeName) {
        this.cartTypeName = cartTypeName;
    }

    @Override
    public String toString() {
        return "BzRegisterTaskDto{" +
                "statusName='" + statusName + '\'' +
                ", tagsTypeName='" + tagsTypeName + '\'' +
                ", cartTypeName='" + cartTypeName + '\'' +
                '}';
    }
}
