package com.jackssy.weibo.util;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class WeiBoUtil {

    static Base64.Decoder decoder = Base64.getDecoder();
    static Base64.Encoder encoder = Base64.getEncoder();

    public static Map<String,String> regionMap = new HashMap();
    static {
        regionMap.put("530000","云南");
        regionMap.put("310000","上海");
        regionMap.put("110000","北京");
        regionMap.put("140000","海南");
        regionMap.put("610000","陕西");
        regionMap.put("350000","福建");
        regionMap.put("440000","广东");
        regionMap.put("150000","内蒙古");
        regionMap.put("210000","辽宁");
        regionMap.put("370000","山东");
        regionMap.put("330000","浙江");
        regionMap.put("340000","安徽");
        regionMap.put("320000","江苏");
        regionMap.put("360000","江西");
    }


    public static String getJsonByRegex(String json) {
        String s = json.replaceAll("\\(|\\)", "");
        String replaceAll = s.replaceAll("sinaSSOController.callbackLoginStatus", "");
        return replaceAll;
    }

    public static String getJsonByRegexEnd(String json) {
        String s = json.replaceAll("\\(|\\)", "");
        String replaceAll = s.replaceAll("parent.sinaSSOController.feedBackUrlCallBack", "");
        String s1 = replaceAll.replaceAll(";", "");
        return s1;
    }

    public static String getNowDate(long times){
        String res = "";
        try {
            Date date = new Date(times);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            res = dateFormat.format(date);
        } catch (Exception var6) {
            var6.printStackTrace();
        }
        return res;
    }

    public static String getUserName(String username){
        try {
            return encoder.encodeToString(username.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return username;
    }

    public static void main(String[] args) {
        System.out.println(getUserName("15810302515"));
    }
}