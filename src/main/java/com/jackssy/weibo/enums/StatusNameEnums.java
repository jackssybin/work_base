package com.jackssy.weibo.enums;

public enum StatusNameEnums {
    STATUS_NAME_UNDO("待执行", 0),
    STATUS_NAME_DOING("执行中", 1),
    STATUS_NAME_PAUSE("暂停", 2),
    STATUS_NAME_DONE("终止", 3),
    STATUS_NAME_ERROR("异常",9);

    private String name;
    private Integer value;

    StatusNameEnums(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static String getNameByValue(Integer value) {
        StatusNameEnums[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            StatusNameEnums dict = var1[var3];
            if (value.equals(dict.getValue())) {
                return dict.getName();
            }
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
