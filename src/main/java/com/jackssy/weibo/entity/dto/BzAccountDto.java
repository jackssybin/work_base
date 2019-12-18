package com.jackssy.weibo.entity.dto;

import com.jackssy.weibo.entity.BzAccount;
import lombok.Data;

/**
 * Created by lh on 2019/12/4.
 */
@Data
public class BzAccountDto extends BzAccount {
    private String statusName;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
