package com.upu.zenka.broadcasttest.bean;

import java.util.List;

/**
 * Created by ThinkPad on 2018/12/25.
 */

public class BkbroadcastBean {
    private String message;
    private String serverTime;
    private int code;

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

    public List<BroadcastmusicBean> getData() {
        return data;
    }

    public void setData(List<BroadcastmusicBean> data) {
        this.data = data;
    }

    private List<BroadcastmusicBean> data;
}
