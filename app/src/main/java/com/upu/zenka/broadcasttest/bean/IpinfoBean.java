package com.upu.zenka.broadcasttest.bean;

/**
 * Created by ThinkPad on 2018/12/13.
 */

public class IpinfoBean{
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    private String hostname;
    public IpinfoBean(){

    }
    public IpinfoBean(String ip,String hostname){
        this.ip=ip;
        this.hostname=hostname;
    }
}
