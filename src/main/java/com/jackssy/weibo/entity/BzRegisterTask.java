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
 * @since 2020-04-18
 */
@TableName("bz_register_task")
public class BzRegisterTask extends Model<BzRegisterTask> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 注册使用的卡农项目id，去卡农客户端查找
     */
    @TableField("register_project")
    private Integer registerProject;

    /**
     * 这个去微博注册找对应的下拉手机号前缀，如英国的前缀0044
     */
    @TableField("phone_country_pre")
    private String phoneCountryPre;

    /**
     * 这个去微博注册找对应的下拉中文如:英国
     */
    @TableField("phone_country_name")
    private String phoneCountryName;

    /**
     * 所属城市code
     */
    @TableField("region_id")
    private String regionId;

    /**
     * 所属城市name
     */
    @TableField("region_name")
    private String regionName;

    /**
     * 注册总数量
     */
    @TableField("register_count")
    private Integer registerCount;

    /**
     * 完成数量
     */
    @TableField("finish_count")
    private Integer finishCount;

    /**
     * 0:待执行,1:执行中2:暂停 3:终止 9:异常
     */
    private Integer status;

    /**
     * 账号标记
     */
    @TableField("register_tag")
    private String registerTag;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 卡商类型
     */
    @TableField("card_type")
    private String cardType;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;

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

    public Integer getRegisterProject() {
        return registerProject;
    }

    public void setRegisterProject(Integer registerProject) {
        this.registerProject = registerProject;
    }

    public String getPhoneCountryPre() {
        return phoneCountryPre;
    }

    public void setPhoneCountryPre(String phoneCountryPre) {
        this.phoneCountryPre = phoneCountryPre;
    }

    public String getPhoneCountryName() {
        return phoneCountryName;
    }

    public void setPhoneCountryName(String phoneCountryName) {
        this.phoneCountryName = phoneCountryName;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getRegisterCount() {
        return registerCount;
    }

    public void setRegisterCount(Integer registerCount) {
        this.registerCount = registerCount;
    }

    public Integer getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(Integer finishCount) {
        this.finishCount = finishCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRegisterTag() {
        return registerTag;
    }

    public void setRegisterTag(String registerTag) {
        this.registerTag = registerTag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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

    public static final String REGISTER_PROJECT = "register_project";

    public static final String PHONE_COUNTRY_PRE = "phone_country_pre";

    public static final String PHONE_COUNTRY_NAME = "phone_country_name";

    public static final String REGION_ID = "region_id";

    public static final String REGION_NAME = "region_name";

    public static final String REGISTER_COUNT = "register_count";

    public static final String FINISH_COUNT = "finish_count";

    public static final String STATUS = "status";

    public static final String REGISTER_TAG = "register_tag";

    public static final String REMARK = "remark";

    public static final String CART_TYPE = "cart_type";

    public static final String START_TIME = "start_time";

    public static final String END_TIME = "end_time";

    public static final String UPDATE_DATE = "update_date";

    public static final String CREATE_DATE = "create_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BzRegisterTask{" +
        ", id=" + id +
        ", registerProject=" + registerProject +
        ", phoneCountryPre=" + phoneCountryPre +
        ", phoneCountryName=" + phoneCountryName +
        ", regionId=" + regionId +
        ", regionName=" + regionName +
        ", registerCount=" + registerCount +
        ", finishCount=" + finishCount +
        ", status=" + status +
        ", registerTag=" + registerTag +
        ", remark=" + remark +
        ", cardType=" + cardType +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", updateDate=" + updateDate +
        ", createDate=" + createDate +
        "}";
    }
}
