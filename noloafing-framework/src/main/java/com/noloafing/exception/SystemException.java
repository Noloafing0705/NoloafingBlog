package com.noloafing.exception;

import com.noloafing.enums.AppHttpCodeEnum;

/**
 * 封装RuntimeException异常类 为全局处理异常提供处理
 */
public class SystemException extends RuntimeException{

    private int code;
    private String msg;
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
    public SystemException(AppHttpCodeEnum appHttpCodeEnum){
        this.code = appHttpCodeEnum.getCode();
        this.msg = appHttpCodeEnum.getMsg();
    }
}
