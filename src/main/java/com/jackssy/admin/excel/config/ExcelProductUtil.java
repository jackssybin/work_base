package com.jackssy.admin.excel.config;

import java.util.ArrayList;
import java.util.List;

public class ExcelProductUtil {

    public static List<List<String>> createTestListStringjakssyHead(){
        //写sheet3  模型上没有注解，表头数据动态传入
        List<List<String>> head = new ArrayList<List<String>>();
        List<String> headCoulumn1 = new ArrayList<String>();
        List<String> headCoulumn2 = new ArrayList<String>();
        List<String> headCoulumn3 = new ArrayList<String>();
        List<String> headCoulumn4 = new ArrayList<String>();
        List<String> headCoulumn5 = new ArrayList<String>();

        headCoulumn1.add("第1列");
        headCoulumn2.add("第2列");

        headCoulumn3.add("第3列");
        headCoulumn4.add("第4列");
        headCoulumn5.add("第5列");

        head.add(headCoulumn1);
        head.add(headCoulumn2);
        head.add(headCoulumn3);
        head.add(headCoulumn4);
        head.add(headCoulumn5);
        return head;
    }

    public static List<List<String>> createProductHeadColumnName(List<String> headerList){
        List<List<String>> head = new ArrayList<List<String>>();
        for (String headerName:headerList){
            addDynamicHead(headerName,head);
        }
        return head;
    }


    public static void addDynamicHead(String header,List<List<String>> head){
        List<String> headCoulumn = new ArrayList<String>();
        headCoulumn.add(header);
        head.add(headCoulumn);
    }

    public static List<List<Object>> createProductColumnRow(List<String> rowList) {
        List<List<Object>> object = new ArrayList<List<Object>>();
        for (int i = 0; i < 1000; i++) {
            List<Object> da = new ArrayList<Object>();
            da.add("158"+i);
            da.add("http://www.baidu.com/"+i);
            da.add("http://www.baidu.com/"+i);
            da.add("http://www.baidu.com/"+i);
            da.add("http://www.baidu.com/"+i);
            object.add(da);
        }

        return object;
    }
}
