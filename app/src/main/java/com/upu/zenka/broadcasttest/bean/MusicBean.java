package com.upu.zenka.broadcasttest.bean;

import android.os.Environment;

/**
 * Created by ThinkPad on 2018/12/25.
 */

public class MusicBean {
    private static final String BASE_PATH= Environment.getExternalStorageDirectory()+"/voicebox/bkmusic/";
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String path;
    private long len;

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    private int volume;

    public String getPath() {
        return path;
    }

    public void setPath(String musicname) {
        this.path = musicname;
    }

    public long getLength() {
        return len*1000;
    }

    public void setLength(long length) {
        this.len = length;
    }

    public boolean isplayed() {
        return isplayed;
    }

    public void setIsplayed(boolean isplayed) {
        this.isplayed = isplayed;
    }

    private boolean isplayed=false;

    public MusicBean(String musicpath,int length,boolean isplayed){
        this.path=musicpath;
        this.len=length;
        this.isplayed=isplayed;
    }

    public String getNativepath() {
        return BASE_PATH+name;
    }

    public void setNativepath(String nativepath) {
        this.nativepath = nativepath;
    }

    private String nativepath;
}
