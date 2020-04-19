package com.jackssy.weibo.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CardTypeEnums {
    EASY_180_CARD_TYPE("蓝天多线程", "EASY_180"),
    DA_XIANG_CARD_TYPE("大象", "DA_XIANG"),
    KA_NONG_CARD_TYPE("卡农", "KA_NONG"),
    LAN_HU_CARD_TYPE("蓝狐", "LAN_HU"),
    MA_DA_SHUAI_CARD_TYPE("马大帅","MA_DA_SHUAI");

    private String name;
    private String value;

    CardTypeEnums(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static String getNameByValue(String value) {
        CardTypeEnums[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            CardTypeEnums dict = var1[var3];
            if (value.equals(dict.getValue())) {
                return dict.getName();
            }
        }
        return null;
    }

    public static List<EnumDto> getEnumdtoList(){
        List<EnumDto> list = new ArrayList<>();
        CardTypeEnums[] var1 = values();
        Arrays.stream(var1).forEach(dict->
                list.add(new EnumDto(dict.getName(),dict.getValue())));
        return list;
    }

    public static void main(String[] args) {
        System.out.println(CardTypeEnums.getEnumdtoList());
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
    }
}
