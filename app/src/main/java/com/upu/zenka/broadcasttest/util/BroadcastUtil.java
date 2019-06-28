package com.upu.zenka.broadcasttest.util;

import android.util.Log;

import com.upu.zenka.broadcasttest.bean.BroadcastmusicBean;
import com.upu.zenka.broadcasttest.bean.MusicBean;

import java.util.ArrayList;
import java.util.List;

import static com.upu.zenka.broadcasttest.tools.getTime.getCurtime;
import static com.upu.zenka.broadcasttest.tools.getTime.getLongtime;

/**
 * Created by ThinkPad on 2018/12/23.
 */

public class BroadcastUtil {
    public static final String TAG="BroadcastUtil";
    private List<BroadcastmusicBean> broadcastProgramList;
    public BroadcastUtil(){
        broadcastProgramList=new ArrayList<BroadcastmusicBean>();
    }
    private boolean isReading=false;
    private List<String> musicReadyList=new ArrayList<String>();
    public void addBroadcast(BroadcastmusicBean broadcastmusicBean){
        broadcastProgramList.add(broadcastmusicBean);
    }
    public void addMusicReadyList(String filename){
        if (!musicReadyList.contains(filename)){
            musicReadyList.add(filename);
        }
    }
    public boolean have(String filename){
        if (musicReadyList.contains(filename)){
            return true;
        }
        return  false;
    }

    public String isHavePlay(){
        isReading=true;
        String res=null;//1-1-30
        for (int i=0;i<broadcastProgramList.size();i++){
            Log.e(TAG,"broadcastProgramList.size="+broadcastProgramList.size());
            if (getCurtime()>=getLongtime(broadcastProgramList.get(i).getPlaytime())){
                //判断应该播放该发节目中的那首歌，及其播放位置
                long distance=getCurtime()-getLongtime(broadcastProgramList.get(i).getPlaytime());
                if (distance>broadcastProgramList.get(i).getTotalLength()){
                    continue;
                }
                if (getMusic(distance,broadcastProgramList.get(i))==null){
                    break;
                }
                res=i+"@"+getMusic(distance,broadcastProgramList.get(i));
                break;
            }
        }
        Log.e(TAG,"broadcastProgramList.size="+broadcastProgramList.size());
        isReading=false;
        return res;
    }
    private String getMusic(long distance,BroadcastmusicBean broadcastmusicBean){
        int musicIndex=-1;
        long position=-1;
        for (int i=0;i<broadcastmusicBean.getMusicList().size();i++){
            long preMusiclength=0;
            for (int j=0;j<i;j++){
                preMusiclength+=broadcastmusicBean.getMusicList().get(j).getLength();
            }
            long suffMusiclength=0;
            for (int j=i+1;j<broadcastmusicBean.getMusicList().size();j++){
                suffMusiclength+=broadcastmusicBean.getMusicList().get(j).getLength();
            }
            if (preMusiclength<=distance&&distance<broadcastmusicBean.getTotalLength()-suffMusiclength){
                musicIndex=i;
                position=distance-preMusiclength;
                return musicIndex+"@"+position+"@"+broadcastmusicBean.getMusicList().get(musicIndex).getName();
            }
        }
        if (musicIndex==-1||position==-1){
            return null;
        }
        return musicIndex+"@"+position+"@"+broadcastmusicBean.getMusicList().get(musicIndex).getName();
    }
    public boolean curProgramFinish(int programIndex){
        boolean isfinish=true;
        if (broadcastProgramList.get(programIndex)!=null){
            for (int i=0;i<broadcastProgramList.get(programIndex).getMusicList().size();i++){
                if (!broadcastProgramList.get(programIndex).getMusicList().get(i).isplayed()){
                    isfinish=false;
                    break;
                }
            }
        }
        return isfinish;
    }
    public void setBroadcastmusic(int index){
        broadcastProgramList.get(index).setPlayed(true);
    }
    public void setMusicPlayed(int programIndex,int musicIndex){
        if (broadcastProgramList.get(programIndex).getMusicList().get(musicIndex)!=null){
            broadcastProgramList.get(programIndex).getMusicList().get(musicIndex).setIsplayed(true);
        }
    }
    public MusicBean getBroadcastmusic(int programIndex, int musicIndex){
        return broadcastProgramList.get(programIndex).getMusicList().get(musicIndex);
    }
    public int getMusiCount(){
        return broadcastProgramList.size();
    }
    public void clear(){
        try {
            while (isReading) {
                Log.e(TAG,"正在查看，不能清空");
                Thread.sleep(10);
            }
            Log.e(TAG,"正在清空");
            broadcastProgramList.clear();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
