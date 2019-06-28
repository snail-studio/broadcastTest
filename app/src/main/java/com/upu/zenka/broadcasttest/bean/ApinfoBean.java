package com.upu.zenka.broadcasttest.bean;

/**
 * Created by ThinkPad on 2019/1/8.
 */

public class ApinfoBean {
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ApResBean getData() {
        return data;
    }

    public void setData(ApResBean data) {
        this.data = data;
    }

    private String message;
    private String serverTime;
    private int code;
    private ApResBean data;
}
