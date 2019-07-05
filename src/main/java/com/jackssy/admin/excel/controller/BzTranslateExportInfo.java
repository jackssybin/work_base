package com.jackssy.admin.excel.controller;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.util.Date;

public class BzTranslateExportInfo extends BaseRowModel {

    @ExcelProperty(value = "产品名称" ,index = 0)
    private String productName;
    @ExcelProperty(value = "手机号" ,index = 1)
    private String phoneNumber;
    @ExcelProperty(value = "pv" ,index = 2)
    private Double pvNumber;
    @ExcelProperty(value = "点击次数" ,index = 3)
    private Integer clickNumber;
    @ExcelProperty(value = "手机系统" ,index = 4)
    private String mobileSystem;
    @ExcelProperty(value = "手机浏览器版本" ,index = 5)
    private String mobileVersion;
    @ExcelProperty(value = "手机品牌" ,index = 6)
    private String mobileBrand;
    @ExcelProperty(value = "点击时间" ,index = 7)
    private Date gmtCreate;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getPvNumber() {
        return pvNumber;
    }

    public void setPvNumber(Double pvNumber) {
        this.pvNumber = pvNumber;
    }

    public Integer getClickNumber() {
        return clickNumber;
    }

    public void setClickNumber(Integer clickNumber) {
        this.clickNumber = clickNumber;
    }

    public String getMobileSystem() {
        return mobileSystem;
    }

    public void setMobileSystem(String mobileSystem) {
        this.mobileSystem = mobileSystem;
    }

    public String getMobileVersion() {
        return mobileVersion;
    }

    public void setMobileVersion(String mobileVersion) {
        this.mobileVersion = mobileVersion;
    }

    public String getMobileBrand() {
        return mobileBrand;
    }

    public void setMobileBrand(String mobileBrand) {
        this.mobileBrand = mobileBrand;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        return "BzTranslateExportInfo{" +
                "productName=" + productName +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", pvNumber=" + pvNumber +
                ", clickNumber=" + clickNumber +
                ", mobileSystem='" + mobileSystem + '\'' +
                ", mobileVersion='" + mobileVersion + '\'' +
                ", mobileBrand='" + mobileBrand + '\'' +
                ", gmtCreate=" + gmtCreate +
                '}';
    }
}
