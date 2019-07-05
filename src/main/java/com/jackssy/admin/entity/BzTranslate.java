package com.jackssy.admin.entity;

import java.util.Date;

public class BzTranslate {

    private Integer productId;
    private String phoneNumber;
    private Double pvNumber;
    private Integer clickNumber;
    private String mobileSystem;
    private String mobileVersion;
    private String mobileBrand;
    private Date gmtCreate;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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
        return "BzTranslate{" +
                "productId=" + productId +
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
