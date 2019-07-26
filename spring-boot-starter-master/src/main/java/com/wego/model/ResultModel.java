package com.wego.model;

/**
 * @author elizayuan
 * 主要用于跟前端实体的对接,返回值
 */
public class ResultModel {
    int code;
    String message;
    int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

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

}
