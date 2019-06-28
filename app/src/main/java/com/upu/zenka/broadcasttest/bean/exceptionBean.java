package com.upu.zenka.broadcasttest.bean;

/**
 * Created by ThinkPad on 2018/11/22.
 */

public class exceptionBean {
    private String data;
    private int code;
    private String message;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    private String serverTime;
}