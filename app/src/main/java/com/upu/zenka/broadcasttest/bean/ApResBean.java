package com.upu.zenka.broadcasttest.bean;

/**
 * Created by ThinkPad on 2019/1/8.
 */

public class ApResBean {
    private int enabled;
    private String ssid;
    private String psw;

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public int getSafetype() {
        return safetype;
    }

    public void setSafetype(int safetype) {
        this.safetype = safetype;
    }

    private int safetype;
}
