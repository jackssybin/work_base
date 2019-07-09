package com.jackssy.admin.entity.vo;

public class ProductTranslateVo {
    private Integer productId;
    private String productName;
    private Integer pvNumber;

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

    @Override
    public String toString() {
        return "ProductTranslateVo{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", pvNumber=" + pvNumber +
                '}';
    }
}
