package com.jackssy.weibo.enums;

/**
 * Created by lh on 2019/12/4.
 */
public enum  CommonLogEnums {
        UNSET("未设置",0),
        SUCCESS("成功",1),
        Fail("失败",9);

        private String Status;
        private Integer Value;

    public static String getStatusByValue(Integer value){
        CommonLogEnums[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            CommonLogEnums dict = var1[var3];
            if (value.equals(dict.getValue())) {
                return dict.getStatus();
            }
        }

        return null;
    }

    CommonLogEnums(String name, Integer value) {
        Status = name;
        Value = value;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Integer getValue() {
        return Value;
    }

    public void setValue(Integer value) {
        Value = value;
    }}
