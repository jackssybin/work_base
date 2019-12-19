package com.jackssy.weibo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jackssy
 * @since 2019-12-19
 */
@TableName("bz_region")
public class BzRegion extends Model<BzRegion> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 城市code
     */
    @TableField("region_code")
    private String regionCode;

    /**
     * 城市名称
     */
    @TableField("region_name")
    private String regionName;

    @TableField("region_order")
    private Integer regionOrder;

    /**
     * 是否启用，0未启用 1:已经启用
     */
    @TableField("is_use")
    private Integer isUse;

    private String remark;

    @TableField("update_date")
    private LocalDateTime updateDate;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getRegionOrder() {
        return regionOrder;
    }

    public void setRegionOrder(Integer regionOrder) {
        this.regionOrder = regionOrder;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public static final String ID = "id";

    public static final String REGION_CODE = "region_code";

    public static final String REGION_NAME = "region_name";

    public static final String REGION_ORDER = "region_order";

    public static final String IS_USE = "is_use";

    public static final String REMARK = "remark";

    public static final String UPDATE_DATE = "update_date";

    public static final String CREATE_DATE = "create_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BzRegion{" +
        ", id=" + id +
        ", regionCode=" + regionCode +
        ", regionName=" + regionName +
        ", regionOrder=" + regionOrder +
        ", isUse=" + isUse +
        ", remark=" + remark +
        ", updateDate=" + updateDate +
        ", createDate=" + createDate +
        "}";
    }
}
