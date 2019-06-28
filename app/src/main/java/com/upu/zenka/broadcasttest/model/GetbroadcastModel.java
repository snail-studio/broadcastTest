package com.upu.zenka.broadcasttest.model;

import com.upu.zenka.broadcasttest.bean.ApinfoBean;
import com.upu.zenka.broadcasttest.bean.BkbroadcastBean;
import com.upu.zenka.broadcasttest.bean.DownloadBean;
import com.upu.zenka.broadcasttest.bean.broadcastBean;
import com.upu.zenka.broadcasttest.bean.exceptionBean;
import com.upu.zenka.broadcasttest.bean.nettestBean;
import com.upu.zenka.broadcasttest.bean.uploadBean;
import com.upu.zenka.broadcasttest.bean.vertifyBean;
import com.upu.zenka.broadcasttest.callback.ModelCallBack;
import com.upu.zenka.broadcasttest.tools.GetDataInterface;
import com.upu.zenka.broadcasttest.tools.publicParam;
import com.upu.zenka.broadcasttest.util.OffretrofitUtils;
import com.upu.zenka.broadcasttest.util.RetrofitUnitl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

/**
 * Created by ThinkPad on 2018/11/13.
 */

public class GetbroadcastModel {
    public void getApinfo(String schoolid,String deviceid,final ModelCallBack.getApinfoCallBack callBack){
        OkHttpClient ok=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();

        Map<String,String> map=new HashMap<>();
        map.put("schoolid",schoolid);
        map.put("deviceid",deviceid);

        RetrofitUnitl.getInstance(publicParam.BASE_URL,ok)
                .setCreate(GetDataInterface.class)
                .getapinfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApinfoBean>(){
                    @Override
                    public void accept(ApinfoBean apinfoBean) throws Exception {
                        callBack.success(apinfoBean);
                    }
                },new Consumer<Throwable>(){
                    @Override
                    public void accept(Throwable throwable) throws Exception{
                        callBack.failed(throwable);
                    }
                });

    }
    public void getBkBroadcastData(String schoolid,String classid,final ModelCallBack.getBkBroadcastCallBack callBack){
        OkHttpClient ok=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();

        Map<String,String> map=new HashMap<>();
        map.put("schoolid",schoolid);
        map.put("classid",classid);

        RetrofitUnitl.getInstance(publicParam.BASE_URL,ok)
                .setCreate(GetDataInterface.class)
                .getBkbroadcast(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BkbroadcastBean>(){
                    @Override
                    public void accept(BkbroadcastBean bkbroadcastBean) throws Exception {
                        callBack.success(bkbroadcastBean);
                    }
                },new Consumer<Throwable>(){
                    @Override
                    public void accept(Throwable throwable) throws Exception{
                        callBack.failed(throwable);
                    }
                });

    }
    public void getBroadcastData(String schoolid,String classid,final ModelCallBack.BroadcastCallBack callBack){
        OkHttpClient ok=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();

        Map<String,String> map=new HashMap<>();
        map.put("classid",classid);
        map.put("schoolid",schoolid);

        RetrofitUnitl.getInstance(publicParam.BASE_URL,ok)
                .setCreate(GetDataInterface.class)
                .getbroadcast(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<broadcastBean>(){
                    @Override
                    public void accept(broadcastBean broadcastbean) throws Exception {
                        callBack.success(broadcastbean);
                        System.out.println("广播信息:"+broadcastbean.toString());
                    }
                },new Consumer<Throwable>(){
                    @Override
                    public void accept(Throwable throwable) throws Exception{
                        callBack.failed(throwable);
                    }
                });

    }
    public void getOffBroadcastData(String schoolid,String classid,String serverip,boolean iscreat,final ModelCallBack.BroadcastCallBack callBack){
        OkHttpClient ok=new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .build();

        Map<String,String> map=new HashMap<>();
        if (classid==null){
            map.put("classid","");
        }else{
            map.put("classid",classid);
        }
        map.put("schoolid",schoolid);

        OffretrofitUtils.getInstance("http://"+serverip+":8080/",ok,iscreat)
                .setCreate(GetDataInterface.class)
                .getOfflinebroadcast("",map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<broadcastBean>(){
                    @Override
                    public void accept(broadcastBean broadcastbean) throws Exception {
                        callBack.success(broadcastbean);
                        System.out.println("广播信息:"+broadcastbean.toString());
                    }
                },new Consumer<Throwable>(){
                    @Override
                    public void accept(Throwable throwable) throws Exception{
                        callBack.failed(throwable);
                    }
                });

    }

    public void vertify(final String deviceid,final ModelCallBack.VertifyCallBack callBack){
        OkHttpClient ok=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();

        RetrofitUnitl.getInstance(publicParam.BASE_URL,ok)
                .setCreate(GetDataInterface.class)
                .verify(deviceid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<vertifyBean>(){
                    @Override
                    public void accept(vertifyBean vertifybean) throws Exception {
                        callBack.success(vertifybean);
                    }
                },new Consumer<Throwable>(){
                    @Override
                    public void accept(Throwable throwable) throws Exception{
                        callBack.failed(throwable);
                    }
                });

    }

    public void netTest(String deviceid,String schoolid,final ModelCallBack.netTestCallBack callBack){
        OkHttpClient ok=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();

        Map<String,String> map=new HashMap<>();
        map.put("deviceid",deviceid);
        map.put("schoolid",schoolid);

        RetrofitUnitl.getInstance(publicParam.BASE_URL,ok)
                .setCreate(GetDataInterface.class)
                .netTest(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<nettestBean>(){
                    @Override
                    public void accept(nettestBean nettestbean) throws Exception {
                        callBack.success(nettestbean);
                    }
                },new Consumer<Throwable>(){
                    @Override
                    public void accept(Throwable throwable) throws Exception{
                        callBack.failed(throwable);
                    }
                });

    }
    public void sentException(String appname,String deviceid,String exception,String time,final ModelCallBack.sentExceptionCallBack callBack){
        OkHttpClient ok=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();

        Map<String,String> map=new HashMap<>();
        map.put("app",appname);
        map.put("deviceid", deviceid);
        map.put("exception", exception);
        map.put("exception_time",time);

        RetrofitUnitl.getInstance(publicParam.BASE_URL,ok)
                .setCreate(GetDataInterface.class)
                .sentException(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<exceptionBean>(){
                    @Override
                    public void accept(exceptionBean exceptionbean) throws Exception {
                        callBack.success(exceptionbean);
                    }
                },new Consumer<Throwable>(){
                    @Override
                    public void accept(Throwable throwable) throws Exception{
                        callBack.failed(throwable);
                    }
                });

    }
    public void downLodaServerInfo(String deviceid,final ModelCallBack.downloadInfoCallBack callBack){
        OkHttpClient ok=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();

        Map<String,String> map=new HashMap<>();
        map.put("deviceid", deviceid);

        RetrofitUnitl.getInstance(publicParam.BASE_URL,ok)
                .setCreate(GetDataInterface.class)
                .downloadInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DownloadBean>(){
                    @Override
                    public void accept(DownloadBean downloadBean) throws Exception {
                        callBack.success(downloadBean);
                    }
                },new Consumer<Throwable>(){
                    @Override
                    public void accept(Throwable throwable) throws Exception{
                        callBack.failed(throwable);
                    }
                });

    }
    public void upload(String deviceid,String ip,String version,final ModelCallBack.uploadInfoCallBack callBack){
        OkHttpClient ok=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();

        Map<String,String> map=new HashMap<>();
        map.put("deviceid", deviceid);
        map.put("ip",ip);
        map.put("version",version);

        RetrofitUnitl.getInstance(publicParam.BASE_URL,ok)
                .setCreate(GetDataInterface.class)
                .upload(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<uploadBean>(){
                    @Override
                    public void accept(uploadBean uploadbean) throws Exception {
                        callBack.success(uploadbean);
                    }
                },new Consumer<Throwable>(){
                    @Override
                    public void accept(Throwable throwable) throws Exception{
                        callBack.failed(throwable);
                    }
                });

    }
}
