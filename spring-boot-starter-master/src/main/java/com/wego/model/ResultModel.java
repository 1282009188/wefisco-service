package com.wego.model;

/**
 * @author elizayuan
 * 主要用于跟前端实体的对接,返回值
 */
public class ResultModel <T>{
    int code;
    String message;
    T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
