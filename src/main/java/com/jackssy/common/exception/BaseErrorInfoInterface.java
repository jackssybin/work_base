package com.jackssy.common.exception;

/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2019/11/26 17:32
 */
public interface BaseErrorInfoInterface {
    /** 错误码*/
    String getResultCode();

    /** 错误描述*/
    String getResultMsg();
}