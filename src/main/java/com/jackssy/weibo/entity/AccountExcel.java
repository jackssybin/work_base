package com.jackssy.weibo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * Created by lh on 2019/12/9.
 */

@Data
public class AccountExcel  extends BaseRowModel {

    @ExcelProperty(index = 0 ,value="登录名")
    private String accountUser;

    @ExcelProperty(index = 1 ,value="登录密码")
    private String accountPwd;

    @ExcelProperty(index = 2 ,value="用户来源")
    private String userSource;

    @ExcelProperty(index = 3 ,value="用户标签")
    private String tagGroup;
}
