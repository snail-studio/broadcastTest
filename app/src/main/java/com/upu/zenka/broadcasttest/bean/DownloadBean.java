package com.upu.zenka.broadcasttest.bean;

import java.util.List;

/**
 * Created by ThinkPad on 2018/12/6.
 */

public class DownloadBean {

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    private Data data;
    private int code;
    private String message;
    private String serverTime;
    public class Data{
        public List<IpinfoBean> getIpinfo() {
            return ipinfo;
        }

        public void setIpinfo(List<IpinfoBean> ipinfo) {
            this.ipinfo = ipinfo;
        }

        private List<IpinfoBean> ipinfo;

    }

}
