package com.jackssy.admin.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;



/**
 * 系统用户
 *
 * @author jackssy
 * @email 27208461@qq.com
 * @date 2019年7月03日 下午5:09:20
 */
public class BzProductShort implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id",name="productId")
    @TableId(value = "product_id", type = IdType.AUTO)
    private Integer productId;

    /**
     * 产品名称
     */
    @ApiModelProperty(value="产品名称",name="prodctName")
    private String prodctName;

    /**
     * 产品静态url
     */
    @ApiModelProperty(value="产品静态url",name="productUrl")
    private String productUrl;

    /**
     * 上传文件路径
     */
    @ApiModelProperty(value="上传文件路径",name="phonePath")
    private String phonePath;

    /**
     * 上传文件路径
     */
    @ApiModelProperty(value="上传文件路径",name="phoneCount")
    private Integer phoneCount;

    /**
     * 状态 0:已创建，1:跑批处理中，2:跑批完成
     */
    @ApiModelProperty(value="状态 0:已创建，1:跑批处理中，2:跑批完成",name="status")
    private Integer status;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注",name="remark")
    private String remark;

    /**
     * 创建用户id
     */
    @ApiModelProperty(value="创建用户id",name="operateUser")
    private String operateUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间",name="gmtCreate")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间",name="gmtModified")
    private Date gmtModified;


    public BzProductShort() {
        super();
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProdctName(String prodctName) {
        this.prodctName = prodctName;
    }

    public String getProdctName() {
        return prodctName;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setPhonePath(String phonePath) {
        this.phonePath = phonePath;
    }

    public String getPhonePath() {
        return phonePath;
    }

    public void setPhoneCount(Integer phoneCount) {
        this.phoneCount = phoneCount;
    }

    public Integer getPhoneCount() {
        return phoneCount;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }


    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }

    @Override
    public String toString() {
        return "BzProductShort{" +
                "productId=" + productId +
                ", prodctName='" + prodctName + '\'' +
                ", productUrl='" + productUrl + '\'' +
                ", phonePath='" + phonePath + '\'' +
                ", phoneCount=" + phoneCount +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", operateUser='" + operateUser + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
