package com.jackssy.admin.excel.controller;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class ProductShortUrlExportInfo extends BaseRowModel {
    @ExcelProperty(value = "手机号" ,index = 0)
    private String phoneNum;

    @ExcelProperty(value = "短链接",index = 1)
    private String productUrl;


}
