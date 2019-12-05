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
 * @since 2019-11-26
 */
@TableName("bz_log")
public class BzLog extends Model<BzLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 任务名称
     */
    @TableField("task_id")
    private Integer taskId;

    /**
     * 任务名称
     */
    @TableField("account_id")
    private Integer accountId;

    /**
     * 关联账号
     */
    @TableField("account_user")
    private String accountUser;


    /**
     * 城市ID
     */
    @TableField("region_id")
    private String regionId;

    /**
     * 代理地址
     */
    @TableField("proxy_ip")
    private String proxyIp;

    /**
     * 评论内容
     */
    @TableField("comment_content")
    private String commentContent;



    /**
     * 是否登录系统
     */
    @TableField("login_system")
    private Integer loginSystem;

    /**
     * 是否评论,0:未评论 1:评论成功 9:评论失败
     */
    private Integer comment;

    /**
     * 是否关注019
     */
    private Integer focus;

    /**
     * 是否点赞019
     */
    private Integer raises;

    /**
     * 是否转发无评论019
     */
    private Integer forward;

    /**
     * 是否转发有评论019
     */
    @TableField("forward_comment")
    private Integer forwardComment;

    /**
     * 是否收藏019
     */
    private Integer collect;

    /**
     */
    private String remark;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getProxyIp() {
        return proxyIp;
    }

    public void setProxyIp(String proxyIp) {
        this.proxyIp = proxyIp;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountUser() {
        return accountUser;
    }

    public void setAccountUser(String accountUser) {
        this.accountUser = accountUser;
    }

    public Integer getLoginSystem() {
        return loginSystem;
    }

    public void setLoginSystem(Integer loginSystem) {
        this.loginSystem = loginSystem;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getFocus() {
        return focus;
    }

    public void setFocus(Integer focus) {
        this.focus = focus;
    }

    public Integer getRaises() {
        return raises;
    }

    public void setRaises(Integer raises) {
        this.raises = raises;
    }

    public Integer getForward() {
        return forward;
    }

    public void setForward(Integer forward) {
        this.forward = forward;
    }

    public Integer getForwardComment() {
        return forwardComment;
    }

    public void setForwardComment(Integer forwardComment) {
        this.forwardComment = forwardComment;
    }

    public Integer getCollect() {
        return collect;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public static final String ID = "id";

    public static final String TASK_ID = "task_id";

    public static final String ACCOUNT_ID = "account_id";

    public static final String ACCOUNT_USER = "account_user";

    public static final String LOGIN_SYSTEM = "login_system";

    public static final String COMMENT = "comment";

    public static final String FOCUS = "focus";

    public static final String RAISES = "raises";

    public static final String FORWARD = "forward";

    public static final String FORWARD_COMMENT = "forward_comment";

    public static final String COLLECT = "collect";

    public static final String CREATE_DATE = "create_date";

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BzLog{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", accountId=" + accountId +
                ", accountUser=" + accountUser +
                ", loginSystem=" + loginSystem +
                ", comment=" + comment +
                ", focus=" + focus +
                ", raises=" + raises +
                ", forward=" + forward +
                ", forwardComment=" + forwardComment +
                ", collect=" + collect +
                ", remark='" + remark + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
