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

}
