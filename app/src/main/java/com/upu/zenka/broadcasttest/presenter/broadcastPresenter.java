package com.upu.zenka.broadcasttest.presenter;

import android.util.Log;

import com.upu.zenka.broadcasttest.bean.ApinfoBean;
import com.upu.zenka.broadcasttest.bean.BkbroadcastBean;
import com.upu.zenka.broadcasttest.bean.DownloadBean;
import com.upu.zenka.broadcasttest.bean.broadcastBean;
import com.upu.zenka.broadcasttest.bean.exceptionBean;
import com.upu.zenka.broadcasttest.bean.nettestBean;
import com.upu.zenka.broadcasttest.bean.uploadBean;
import com.upu.zenka.broadcasttest.bean.vertifyBean;
import com.upu.zenka.broadcasttest.callback.ModelCallBack;
import com.upu.zenka.broadcasttest.model.GetbroadcastModel;
import com.upu.zenka.broadcasttest.view.MyView;

/**
 * Created by ThinkPad on 2018/11/15.
 */

public class broadcastPresenter {
    private static final String TAG="MainActivity";
    GetbroadcastModel getbroadcastModel=new GetbroadcastModel();
    MyView.BroadcastView broadcastView;
    public broadcastPresenter(MyView.BroadcastView broadcastView){
        this.broadcastView=broadcastView;
    }
    public void getData(String schoolid,String classid){
        getbroadcastModel.getBroadcastData(schoolid,classid,new ModelCallBack.BroadcastCallBack(){
            @Override
            public void success(broadcastBean broadcastbean){
                broadcastView.success(broadcastbean,true);
            }
            @Override
            public void failed(Throwable code){
                broadcastView.failed(10004);
                System.out.println("登录错误："+code);
            }
        });
    }
    public void getOffData(String schoolid,String classid,String serverip,boolean iscreat){
        Log.i(TAG,"sid="+ schoolid+";cid="+classid+";pip="+serverip+";create="+iscreat);
        getbroadcastModel.getOffBroadcastData(schoolid,classid,serverip,iscreat,new ModelCallBack.BroadcastCallBack(){
            @Override
            public void success(broadcastBean broadcastbean){
                broadcastView.success(broadcastbean,false);
            }
            @Override
            public void failed(Throwable code){
                broadcastView.failed(10003);
                System.out.println("登录错误："+code);
            }
        });
    }
    public void verify(String deviceid){
        getbroadcastModel.vertify(deviceid,new ModelCallBack.VertifyCallBack(){
            @Override
            public void success(vertifyBean vertifybean){
                broadcastView.verify(vertifybean);
                System.out.println("登录数据："+vertifybean.toString());
            }
            @Override
            public void failed(Throwable code){
                broadcastView.failed(10002);
                System.out.println("登录错误："+code);
            }
        });
    }
    public void downloadInfo(String deviceid){
        getbroadcastModel.downLodaServerInfo(deviceid,new ModelCallBack.downloadInfoCallBack(){
            @Override
            public void success(DownloadBean downloadBean){
                broadcastView.downloadInfo(downloadBean);
                System.out.println("登录数据："+downloadBean.toString());
            }
            @Override
            public void failed(Throwable code){
                broadcastView.failed(10006);
                System.out.println("登录错误："+code);
            }
        });
    }
    public void netTest(String deviceid,String schoolid){
        getbroadcastModel.netTest(deviceid,schoolid,new ModelCallBack.netTestCallBack(){
            @Override
            public void success(nettestBean nettestbean){
                broadcastView.netTest(nettestbean);
                System.out.println("登录数据："+nettestbean.toString());
            }
            @Override
            public void failed(Throwable code){
                broadcastView.failed(10001);
                System.out.println("登录错误："+code);
            }
        });
    }
    public void sentException(String appname, String deviceid, final String exception, String time, final String filepath){
        getbroadcastModel.sentException(appname,deviceid,exception,time,new ModelCallBack.sentExceptionCallBack(){
            @Override
            public void success(exceptionBean exceptionbean){
                broadcastView.sentException(exceptionbean,filepath);
            }
            @Override
            public void failed(Throwable code){
                System.out.println("登录错误："+code);
            }
        });
    }
    public void upload(String deviceid,String localip,String version){
        getbroadcastModel.upload(deviceid,localip,version,new ModelCallBack.uploadInfoCallBack(){
            @Override
            public void success(uploadBean uploadbean){
                broadcastView.upload(uploadbean);
            }
            @Override
            public void failed(Throwable code){
                broadcastView.failed(10008);
                System.out.println("登录错误："+code);
            }
        });
    }
    public void getBkData(String schoolid,String classid){
        getbroadcastModel.getBkBroadcastData(schoolid,classid,new ModelCallBack.getBkBroadcastCallBack(){
            @Override
            public void success(BkbroadcastBean bkbroadcastBean){
                broadcastView.bkBroadcast(bkbroadcastBean);
            }
            @Override
            public void failed(Throwable code){
                broadcastView.failed(10009);
                System.out.println("登录错误："+code);
            }
        });
    }
    public void getApinfo(String schoolid,String deviceid){
        getbroadcastModel.getApinfo(schoolid,deviceid,new ModelCallBack.getApinfoCallBack(){
            @Override
            public void success(ApinfoBean apinfoBean){
                broadcastView.getApinfo(apinfoBean);
            }
            @Override
            public void failed(Throwable code){
                broadcastView.failed(10009);
                System.out.println("登录错误："+code);
            }
        });
    }
}
