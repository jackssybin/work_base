package com.jackssy.admin.enumtype;

public enum ProductStatusEnum {
    CREATED(0,"已创建"),
    RUNNING(1,"跑批中"),
    DONE(2,"跑批完成");


    private Integer code;
    private String name;

    ProductStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
