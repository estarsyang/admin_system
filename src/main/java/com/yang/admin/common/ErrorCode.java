package com.yang.admin.common;


import lombok.Data;

/**
 *
 */

public enum ErrorCode {

    SUCCESS(0,"ok"),
    PARAMS_ERROR(40000,"params error"),
    NULL_ERROR(40001,"null params"),
    NOT_LOGIN(40100,"not login"),
    NO_AUTH(40100,"没有权限或权限过期"),
    LOGIN_NOT_MATCH(40101,"账号或密码输入错误"),
    SYSTEM_ERROR(50000,"system error"),
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
