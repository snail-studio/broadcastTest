package com.upu.zenka.broadcasttest.bean;

/**
 * Created by ThinkPad on 2019/1/2.
 */

public class DownloadmusicBean {
    private MusicBean musicBean;

    public MusicBean getMusicBean() {
        return musicBean;
    }

    public void setMusicBean(MusicBean musicBean) {
        this.musicBean = musicBean;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }

    private boolean isDownload;

    public DownloadmusicBean(MusicBean musicBean){
        this.musicBean=musicBean;
        this.isDownload=false;
    }
}
