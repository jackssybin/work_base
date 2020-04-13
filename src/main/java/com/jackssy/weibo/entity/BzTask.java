package com.jackssy.weibo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.sf.cglib.core.Local;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author jackssy
 * @since 2019-11-26
 */
@Data
@TableName("bz_task")
public class BzTask extends Model<BzTask> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 任务名称
     */
    @TableField("task_name")
    private String taskName;


    /**
     * 任务名称
     */
    @TableField("task_type")
    private String taskType;

    /**
     * 任务总量
     */
    @TableField("task_count")
    private Integer taskCount;

    /**
     * 任务总量
     */
    @TableField("finish_count")
    private Integer finishCount;

    /**
     * 任务执行速度，1:正常速度模拟人行为,2:快速执行
     */
    @TableField("task_speed")
    private Integer taskSpeed;

    /**
     * 目标url
     */
    @TableField("target_url")
    private String targetUrl;

    /**
     * 目标的第几个微博
     */
    @TableField("target_number")
    private String targetNumber;

    /**
     * 目标所有的关键词
     */
    @TableField("target_title")
    private String targetTitle;

    /**
     * 评论来源类型1:通用评论,2:任务分组评论逗号分隔
     */
    @TableField("comment_type")
    private Integer commentType;

    @TableField("comment_content")
    private String commentContent;

    /**
     * 关联标签类型,多个标签已逗号分隔
     */
    @TableField("tag_group")
    private String tagGroup;


    /**
     * 备注
     */
    private String remark;

    /**
     * 0:待执行,1:执行中2:暂停 3:终止
     */
    private Integer status;

    /**
     * 是否评论
     */
    private Integer comment;

    /**
     * 是否关注
     */
    private Integer focus;

    /**
     * 是否点赞
     */
    private Integer raises;

    /**
     * 是否转发无评论
     */
    private Integer forward;

    /**
     * 是否转发有评论
     */
    private Integer forwardComment;

    /**
     * 是否收藏
     */
    private Integer collect;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    @TableField("pre_task_id")
    private String preTaskId;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * 创建时间
     */
    @TableField("create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;

    @TableField("update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateDate;

    private Integer send;

    private String rndComment;

    private Integer rndCount;

    private String rndTarget;

    private String rndContent;

    public String getPreTaskId() {
        return preTaskId;
    }

    public void setPreTaskId(String preTaskId) {
        this.preTaskId = preTaskId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Integer taskCount) {
        this.taskCount = taskCount;
    }

    public Integer getTaskSpeed() {
        return taskSpeed;
    }

    public void setTaskSpeed(Integer taskSpeed) {
        this.taskSpeed = taskSpeed;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getTargetNumber() {
        return targetNumber;
    }

    public void setTargetNumber(String targetNumber) {
        this.targetNumber = targetNumber;
    }

    public String getTargetTitle() {
        return targetTitle;
    }

    public void setTargetTitle(String targetTitle) {
        this.targetTitle = targetTitle;
    }

    public Integer getCommentType() {
        return commentType;
    }

    public void setCommentType(Integer commentType) {
        this.commentType = commentType;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public static final String TASK_NAME = "task_name";

    public static final String TASK_COUNT = "task_count";

    public static final String TASK_SPEED = "task_speed";

    public static final String TARGET_URL = "target_url";

    public static final String TARGET_NUMBER = "target_number";

    public static final String TARGET_TITLE = "target_title";

    public static final String COMMENT_TYPE = "comment_type";

    public static final String COMMENT_CONTENT = "comment_content";

    public static final String TAGS_TYPE = "tags_type";

    public static final String REMARKS = "remarks";

    public static final String STATUS = "status";

    public static final String COMMENT = "comment";

    public static final String FOCUS = "focus";

    public static final String RAISES = "raises";

    public static final String FORWARD = "forward";

    public static final String FORWARDCOMMENT = "forwardComment";

    public static final String COLLECT = "collect";

    public static final String START_TIME = "start_time";

    public static final String END_TIME = "end_time";

    public static final String CREATE_DATE = "create_date";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BzTask{" +
        ", id=" + id +
        ", taskName=" + taskName +
        ", taskCount=" + taskCount +
        ", taskSpeed=" + taskSpeed +
        ", targetUrl=" + targetUrl +
        ", targetNumber=" + targetNumber +
        ", targetTitle=" + targetTitle +
        ", commentType=" + commentType +
        ", commentContent=" + commentContent +
        ", tagGroup=" + tagGroup +
        ", remark=" + remark +
        ", status=" + status +
        ", comment=" + comment +
        ", focus=" + focus +
        ", raises=" + raises +
        ", forward=" + forward +
        ", forwardComment=" + forwardComment +
        ", collect=" + collect +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", createDate=" + createDate +
        "}";
    }
}
