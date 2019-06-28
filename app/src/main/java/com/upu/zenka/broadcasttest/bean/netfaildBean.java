package com.upu.zenka.broadcasttest.bean;

/**
 * Created by ThinkPad on 2018/12/13.
 */

public class netfaildBean {
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes() {
        this.times = (times+1);
    }

    private int times;
    public netfaildBean(String ip,int times){
        this.ip=ip;
        this.times=times;
    }
}
