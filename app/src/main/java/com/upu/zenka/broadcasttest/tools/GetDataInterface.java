package com.upu.zenka.broadcasttest.tools;

import com.upu.zenka.broadcasttest.bean.ApigetBean;
import com.upu.zenka.broadcasttest.bean.ApinfoBean;
import com.upu.zenka.broadcasttest.bean.BkbroadcastBean;
import com.upu.zenka.broadcasttest.bean.DownloadBean;
import com.upu.zenka.broadcasttest.bean.broadcastBean;
import com.upu.zenka.broadcasttest.bean.exceptionBean;
import com.upu.zenka.broadcasttest.bean.nettestBean;
import com.upu.zenka.broadcasttest.bean.uploadBean;
import com.upu.zenka.broadcasttest.bean.vertifyBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by ThinkPad on 2018/11/13.
 */

public interface GetDataInterface {
    @FormUrlEncoded
    @POST("/index.php/api//broadcast/getBkBroadcast")
    Observable<BkbroadcastBean> getBkbroadcast(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/index.php/api//broadcast/getBroadcast")
    Observable<broadcastBean> getbroadcast(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("{url}")
    Observable<broadcastBean> getOfflinebroadcast(@Path("url") String url,@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/index.php/api//broadcast/verify")
    Observable<vertifyBean> verify(@Field("deviceid") String deviceid);

    @FormUrlEncoded
    @POST("/index.php/api//broadcast/nettest")
    Observable<nettestBean> netTest(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/index.php/api//exception/crash")
    Observable<exceptionBean> sentException(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/index.php/api//broadcast/getNativeServierInfo")
    Observable<DownloadBean> downloadInfo(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/index.php/api//broadcast/uploadinfo")
    Observable<uploadBean> upload(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/index.php/api//broadcast/getapinfo")
    Observable<ApinfoBean> getapinfo(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/broadcast.php")
    Observable<ApigetBean> getApiUrl(@FieldMap Map<String,String> map);

    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String url);
}