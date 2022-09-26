package com.blog.personalblog.entity;

/**
 * 错误码
 * @author: SuperMan
 * @create: 2022-03-14
 **/
public enum ErrorCode {
    SUCCESS("成功", 200),
    NOT_LOGIN("未登录", 100),
    ERROR_CODE("未定义错误", 101),
    USER_NOT_EXIST("用户不存在", 102);



    private int code;
    private String msg;
    private ErrorCode(String msg,int code){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
