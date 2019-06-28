package com.upu.zenka.broadcasttest.bean;

/**
 * Created by ThinkPad on 2018/12/21.
 */

public class DownloadUrl {
    public String getFilaname() {
        return filaname;
    }

    public void setFilaname(String filaname) {
        this.filaname = filaname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String filaname;
    private String url;

    public DownloadUrl(String filaname,String url){
        this.filaname=filaname;
        this.url=url;
    }
}
