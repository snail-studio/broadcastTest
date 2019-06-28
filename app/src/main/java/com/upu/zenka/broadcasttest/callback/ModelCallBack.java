package com.upu.zenka.broadcasttest.callback;

import com.upu.zenka.broadcasttest.bean.ApinfoBean;
import com.upu.zenka.broadcasttest.bean.BkbroadcastBean;
import com.upu.zenka.broadcasttest.bean.DownloadBean;
import com.upu.zenka.broadcasttest.bean.broadcastBean;
import com.upu.zenka.broadcasttest.bean.exceptionBean;
import com.upu.zenka.broadcasttest.bean.nettestBean;
import com.upu.zenka.broadcasttest.bean.uploadBean;
import com.upu.zenka.broadcasttest.bean.vertifyBean;

/**
 * Created by ThinkPad on 2018/11/13.
 */

public class ModelCallBack {
    public interface BroadcastCallBack{
        public void success(broadcastBean broadcastbean);
        public void failed(Throwable code);
    }
    public interface VertifyCallBack{
        public void success(vertifyBean vertifybean);
        public void failed(Throwable code);
    }
    public interface netTestCallBack{
        public void success(nettestBean nettestbean);
        public void failed(Throwable code);
    }
    public interface sentExceptionCallBack{
        public void success(exceptionBean exceptionbean);
        public void failed(Throwable code);
    }
    public interface downloadInfoCallBack{
        public void success(DownloadBean downloadBean);
        public void failed(Throwable code);
    }
    public interface uploadInfoCallBack{
        public void success(uploadBean uploadbean);
        public void failed(Throwable code);
    }
    public interface getBkBroadcastCallBack{
        public void success(BkbroadcastBean bkbroadcastBean);
        public void failed(Throwable code);
    }
    public interface getApinfoCallBack{
        public void success(ApinfoBean apinfoBean);
        public void failed(Throwable code);
    }
}
