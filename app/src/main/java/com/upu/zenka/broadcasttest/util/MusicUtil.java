package com.upu.zenka.broadcasttest.util;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.upu.zenka.broadcasttest.view.MyView;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by ThinkPad on 2018/12/23.
 */

public class MusicUtil {
    public static final String TAG="MusicUtil";
    private MediaPlayer mediaPlayer;
    private static MusicUtil mMusicManager;
    MyView.MusicView musicView;
    private String curmusicPath;
    private int programIndex;
    private int musicIndex;
    private AudioManager audioManager;
    private int volume;
    public MusicUtil(AudioManager audioManager,MyView.MusicView musicView){
        this.audioManager=audioManager;
        this.musicView=musicView;
    }
    private boolean addSource(String path){
        //mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(path);
            curmusicPath=path;
        }catch (IOException e){
            e.printStackTrace();
            return  false;
        }
        return  true;
    }
    public void play(int programIndex,int musicIndex,String path,int position,int volume){
        File file=new File(path);
        if (file==null){
            musicView.playFaild("filenull");
            return;
        }
        this.volume=volume;
        this.programIndex=programIndex;
        this.musicIndex=musicIndex;
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volume,0);
        if(!addSource(path)){
            musicView.playFaild("addSource");
            return;
        }
        try{
            play(position);
        }catch (Exception e) {
            new File(curmusicPath).delete();
            musicView.playFaild("prePlay");
            //musicView.completion(programIndex,musicIndex);
            e.printStackTrace();
        }

    }
    private boolean addSource(List<String> pathlist){
        mediaPlayer.reset();
        try {
            for (int i = 0; i < pathlist.size(); i++) {
                mediaPlayer.setDataSource(pathlist.get(i));
            }
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public void play(final int position){
        Log.e(TAG,"mediaPlayer准备加载");
        try {
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    try {
                        Log.e(TAG, "mediaPlayer加载完成，准备播放");
                        long ss=mediaPlayer.getDuration();
                        mediaPlayer.seekTo(position);
                        mediaPlayer.start();
                        musicView.play();
                    } catch (Exception e) {
                        if (mediaPlayer != null) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer = null;
                            musicView.playFaild("PreparedListener");
                        }
                        e.printStackTrace();
                    }
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    musicView.completion(programIndex, musicIndex);
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    new File(curmusicPath).delete();
                    musicView.playFaild("error");
                    return true;
                }
            });
        }catch (Exception e){
            Log.e(TAG, "mediaPlayer加载异常");
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                musicView.playFaild("prepareAsync");
            }
            e.printStackTrace();
        }
    }
    public void stop(){
        if (mediaPlayer!=null&&mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            musicView.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
    public void pause(){
        if (mediaPlayer!=null&&mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            musicView.pause();
        }
    }
    public void keepup(){
        if (mediaPlayer!=null){
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volume,0);
            mediaPlayer.start();
            musicView.keepup();
        }
    }
    public void release()
    {
        Log.e(TAG,"准备release  mediaPlayer");
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
            musicView.playFaild("release");
        }
    }
}
