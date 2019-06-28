package com.upu.zenka.broadcasttest.bean;

import java.util.List;

/**
 * Created by ThinkPad on 2018/12/23.
 */

public class BroadcastmusicBean {
    private String start;
    private int times;

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    private int volume;

    public String getPlaytime() {
        return start;
    }

    public void setPlaytime(String playtime) {
        this.start = playtime;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public List<MusicBean> getMusicList() {
        return musiclist;
    }

    public void setMusicList(List<MusicBean> musicList) {
        this.musiclist = musicList;
    }

    private List<MusicBean> musiclist;

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }

    private boolean isPlayed=false;

    public long getTotalLength() {
        return totallen*1000;
    }

    public void setTotalLength(long totalLength) {
        this.totallen = totalLength;
    }

    private long totallen;



}
