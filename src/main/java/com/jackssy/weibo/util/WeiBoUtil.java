package com.jackssy.weibo.util;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.TimeZone;


public class WeiBoUtil {

    static Base64.Decoder decoder = Base64.getDecoder();
    static Base64.Encoder encoder = Base64.getEncoder();

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