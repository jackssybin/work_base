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
 * @since 2020-06-02
 */
@TableName("bz_lastread_task")
public class BzLastreadTask extends Model<BzLastreadTask> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户uid逗号分隔
     */
    private String uids;

    /**
     * 阅读总数量
     */
    @TableField("lastread_count")
    private Integer lastreadCount;

    /**
     * 完成数量
     */
    @TableField("finish_count")
    private Integer finishCount;

    /**
     * 0:待执行,1:执行中2:暂停 3:终止 9:异常
     */
    private Boolean status;

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

    public String getUids() {
        return uids;
    }

    public void setUids(String uids) {
        this.uids = uids;
    }

    public Integer getLastreadCount() {
        return lastreadCount;
    }

    public void setLastreadCount(Integer lastreadCount) {
        this.lastreadCount = lastreadCount;
    }

    public Integer getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(Integer finishCount) {
        this.finishCount = finishCount;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public static final String UIDS = "uids";

    public static final String LASTREAD_COUNT = "lastread_count";

    public static final String FINISH_COUNT = "finish_count";

    public static final String STATUS = "status";

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
        return "BzLastreadTask{" +
        ", id=" + id +
        ", uids=" + uids +
        ", lastreadCount=" + lastreadCount +
        ", finishCount=" + finishCount +
        ", status=" + status +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", updateDate=" + updateDate +
        ", createDate=" + createDate +
        "}";
    }
}
