package com.upu.zenka.broadcasttest;

import android.app.Application;
import android.content.Context;

import com.upu.zenka.broadcasttest.tools.CrashHandler;

/**
 * Created by ThinkPad on 2018/11/22.
 */

public class MyApp extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext=this;
        CrashHandler.getInstance().init(this);
        //LogcatHelper.getInstance(this).start();
    }
    public static Context getmContext(){
        return mContext;
    }
}
