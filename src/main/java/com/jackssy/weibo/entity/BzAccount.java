package com.jackssy.weibo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jackssy
 * @since 2019-11-22
 */
@TableName("bz_account")
public class BzAccount extends Model<BzAccount> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 登录名
     */
    @TableField("account_user")
    private String accountUser;

    /**
     * 登录密码
     */
    @TableField("account_pwd")
    private String accountPwd;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 城市id
     */
    @TableField("region_id")
    private String regionId;

    /**
     * 城市名称
     */
    @TableField("region_name")
    private String regionName;

    private String remarks;

    /**
     * 是否正常/可用,封禁
     */
    private Integer status;

    /**
     * 启用/暂停
     */
    @TableField("use_flag")
    private Integer useFlag;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;

    @TableField("update_date")
    private LocalDateTime updateDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountUser() {
        return accountUser;
    }

    public void setAccountUser(String accountUser) {
        this.accountUser = accountUser;
    }

    public String getAccountPwd() {
        return accountPwd;
    }

    public void setAccountPwd(String accountPwd) {
        this.accountPwd = accountPwd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Integer useFlag) {
        this.useFlag = useFlag;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public static final String ID = "id";

    public static final String ACCOUNT_USER = "account_user";

    public static final String ACCOUNT_PWD = "account_pwd";

    public static final String NICK_NAME = "nick_name";

    public static final String SEX = "sex";

    public static final String REGION_ID = "region_id";

    public static final String REGION_NAME = "region_name";

    public static final String REMARKS = "remarks";

    public static final String STATUS = "status";

    public static final String USE_FLAG = "use_flag";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BzAccount{" +
        ", id=" + id +
        ", accountUser=" + accountUser +
        ", accountPwd=" + accountPwd +
        ", nickName=" + nickName +
        ", sex=" + sex +
        ", regionId=" + regionId +
        ", regionName=" + regionName +
        ", remarks=" + remarks +
        ", status=" + status +
        ", useFlag=" + useFlag +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        "}";
    }
}
