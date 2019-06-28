package com.upu.zenka.broadcasttest.bean;

/**
 * Created by ThinkPad on 2018/11/15.
 */

public class BroadcastInfo {
    private String content;
    private int times;
    public BroadcastInfo(String content, int times){
        this.content=content;
        this.times=times;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
    public void broad(){
        times--;
    }
}
