package com.jackssy.weibo.enums;


import org.apache.commons.lang3.StringUtils;

/**
 * Created by lh on 2019/12/10.
 */

public enum TaskTypeEnums {
    COMMENT("评论", "1"),
    RAISES("点赞", "2"),
    FORWARD("转发","3"),
    FOCUS("关注","4");



    private String name;
    private String value;

    TaskTypeEnums(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static String getNameByValue(String value) {
        TaskTypeEnums[] var1 = values();
        int var2 = var1.length;
        String result = "";
        if(StringUtils.isNotBlank(value)){
            for(int var3 = 0; var3 < var2; ++var3) {
                TaskTypeEnums dict = var1[var3];
                if (value.contains(dict.getValue())) {
                    result += dict.getName() +"/";
                }
            }
            if(result.length()>0){
                result = result.substring(0,result.length()-1);
            }
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }}
