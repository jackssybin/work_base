package com.jackssy.weibo.enums;

/**
 * Created by lh on 2019/12/4.
 */
public enum AccountStatusEnums {
    STATUS_NORMAL("正常",1),
    STATUS_USE("占用",2),
    STATUS_UN_FILL("待完善信息",7),
    STATUS_UN_WATCH("待观察",8),
    STATUS_CLOSE("封禁",9),
    STATUS_UNUSE("未启用",0);

    private String status;
    private Integer value;

    AccountStatusEnums(String status, Integer value) {
        this.status = status;
        this.value = value;
    }

    public static String getStatusByValue(Integer value){
        AccountStatusEnums[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            AccountStatusEnums dict = var1[var3];
            if (value.equals(dict.getValue())) {
                return dict.getStatus();
            }
        }

        return null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }}
