package com.upu.zenka.broadcasttest.view;

import com.upu.zenka.broadcasttest.bean.ApinfoBean;
import com.upu.zenka.broadcasttest.bean.BkbroadcastBean;
import com.upu.zenka.broadcasttest.bean.DownloadBean;
import com.upu.zenka.broadcasttest.bean.broadcastBean;
import com.upu.zenka.broadcasttest.bean.exceptionBean;
import com.upu.zenka.broadcasttest.bean.nettestBean;
import com.upu.zenka.broadcasttest.bean.uploadBean;
import com.upu.zenka.broadcasttest.bean.vertifyBean;

/**
 * Created by ThinkPad on 2018/11/15.
 */

public interface MyView {
    public interface BroadcastView {
        void success(broadcastBean broadcastbean,boolean isremote);
        void failed(int code);
        void verify(vertifyBean vertifybean);
        void netTest(nettestBean nettestbean);
        void sentException(exceptionBean exceptionbean,String filepath);
        void downloadInfo(DownloadBean downloadBean);
        void upload(uploadBean uploadbean);
        void bkBroadcast(BkbroadcastBean bkbroadcastBean);
        void getApinfo(ApinfoBean apinfoBean);
    }

    public interface MusicView{
        void play();
        void completion(int programIndex,int musicIndex);
        void error();
        void stop();
        void pause();
        void keepup();
        void playFaild(String code);
    }
}