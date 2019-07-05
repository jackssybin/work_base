package com.jackssy.admin.entity;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;



/**
 * 产品手机号关系表
 *
 * @author jackssy
 * @email 27208461@qq.com
 * @date 2019年7月03日 下午5:56:25
 */
public class BzProductMobile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业务产品id
     */
    @ApiModelProperty(value="业务产品id",name="productId")

    private Integer productId;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号",name="phoneNumber")
    private String phoneNumber;

    /**
     * 短链接url
     */
    @ApiModelProperty(value="短链接url",name="shortUrl")
    private String shortUrl;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间",name="gmtCreate")
    private Date gmtCreate;


    public BzProductMobile() {
        super();
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    @Override
    public String toString() {
        return "BzProductMobile{" +
                "productId=" + productId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                '}';
    }
}
