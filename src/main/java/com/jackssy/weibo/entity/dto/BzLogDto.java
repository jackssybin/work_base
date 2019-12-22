package com.jackssy.weibo.entity.dto;

import com.jackssy.weibo.entity.BzLog;
import lombok.Data;

/**
 * Created by lh on 2019/12/4.
 */
@Data
public class BzLogDto extends BzLog {

    private String loginSystemStatus;

    private String commentStatus;

    private String focusStatus;

    private String raisesStatus;

    private String forwardStatus;

    private String forwardCommentStatus;

    private String collectStatus;

    private String regionName;


    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getLoginSystemStatus() {
        return loginSystemStatus;
    }

    public void setLoginSystemStatus(String loginSystemStatus) {
        this.loginSystemStatus = loginSystemStatus;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getFocusStatus() {
        return focusStatus;
    }

    public void setFocusStatus(String focusStatus) {
        this.focusStatus = focusStatus;
    }

    public String getRaisesStatus() {
        return raisesStatus;
    }

    public void setRaisesStatus(String raisesStatus) {
        this.raisesStatus = raisesStatus;
    }

    public String getForwardStatus() {
        return forwardStatus;
    }

    public void setForwardStatus(String forwardStatus) {
        this.forwardStatus = forwardStatus;
    }

    public String getForwardCommentStatus() {
        return forwardCommentStatus;
    }

    public void setForwardCommentStatus(String forwardCommentStatus) {
        this.forwardCommentStatus = forwardCommentStatus;
    }

    public String getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(String collectStatus) {
        this.collectStatus = collectStatus;
    }
}
