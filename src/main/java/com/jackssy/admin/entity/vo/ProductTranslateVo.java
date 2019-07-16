package com.jackssy.admin.entity.vo;

import com.jackssy.common.util.ArithUtil;

import java.text.DecimalFormat;

public class ProductTranslateVo {
    private Integer productId;
    private String productName;
    private Integer pvNumber;
    private Integer uvNumber;
    private Integer phoneCount;
    private String uvPercent;

    public String getUvPercent() {
        if(null==uvNumber||uvNumber==0||phoneCount==null ||phoneCount==0){
            return "0‰";
        }
        if(null!=phoneCount&&uvNumber!=null&&uvNumber>0&&phoneCount>0){
            return ArithUtil.mul(Double.valueOf(1000),ArithUtil.div(Double.valueOf(uvNumber),Double.valueOf(phoneCount),6))+"‰";
        }

        return uvPercent;
    }

    public void setUvPercent(String uvPercent) {
        this.uvPercent = uvPercent;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPvNumber() {
        return pvNumber;
    }

    public void setPvNumber(Integer pvNumber) {
        this.pvNumber = pvNumber;
    }

    public Integer getUvNumber() {
        return uvNumber;
    }

    public void setUvNumber(Integer uvNumber) {
        this.uvNumber = uvNumber;
    }

    public Integer getPhoneCount() {
        return phoneCount;
    }

    public void setPhoneCount(Integer phoneCount) {
        this.phoneCount = phoneCount;
    }

    @Override
    public String toString() {
        return "ProductTranslateVo{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", pvNumber=" + pvNumber +
                ", uvNumber=" + uvNumber +
                ", phoneCount=" + phoneCount +
                '}';
    }

    public static void main(String[] args) {
        double d = 114.145;
        Integer uv=1;
        Integer count=10000;


        DecimalFormat df = new DecimalFormat("#.00");
        String str = df.format(d);
        System.out.println(str);

        double d2 = 1/10000*1000;
        d2 = (double) Math.round(d2 * 100) / 100;
        System.out.println(d2);

        System.out.println(String.format("%.2f", d));
    }
}
