package com.blog.personalblog.util;

import java.io.Serializable;

/**
 * @author: SuperMan
 * 欢迎关注我的公众号：码上言
 * @create: 2021-11-06
 */
public class JsonResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 成功
     */
    public static final int SUCCESS = 200;
    /**
     * 失败
     */
    public static final int error = 500;
    private int code;
    private String msg;
    private T data;

    public static <T> JsonResult<T> success() {
        return jsonResult(null, SUCCESS, "操作成功");
    }

    public static <T> JsonResult<T> success(T data) {
        return jsonResult(data, SUCCESS, "操作成功");
    }


    public static <T> JsonResult<T> error() {
        return jsonResult(null, error, "操作失败");
    }

    public static <T> JsonResult<T> error(String msg) {
        return jsonResult(null, error, msg);
    }

    public static <T> JsonResult<T> error(T data) {
        return jsonResult(data, error, "操作失败");
    }

    private static <T> JsonResult<T> jsonResult(T data, int code, String msg) {
        JsonResult<T> result = new JsonResult<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
