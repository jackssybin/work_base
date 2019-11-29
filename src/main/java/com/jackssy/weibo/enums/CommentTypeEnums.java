package com.jackssy.weibo.enums;


public enum CommentTypeEnums {
        CONTENT_TYPE_WANNENG("万能评论", 1),
        CONTENT_TYPE_TASK("任务评论", 2);

        private String name;
        private Integer value;

    CommentTypeEnums(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static String getNameByValue(Integer value) {
        CommentTypeEnums[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            CommentTypeEnums dict = var1[var3];
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
