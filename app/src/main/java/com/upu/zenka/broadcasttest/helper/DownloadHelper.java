package com.upu.zenka.broadcasttest.helper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by ThinkPad on 2018/12/21.
 */

public class DownloadHelper {
    private static final String TAG="ApiHelper";
    private static DownloadHelper mInstance;
    private Retrofit mRetrofit;
    private OkHttpClient mHttpClient;

    private DownloadHelper(){
        this(30,30,30);
    }
    public DownloadHelper(int connTimeout,int readTimeout,int writeTimeout){
        OkHttpClient.Builder builder=new OkHttpClient.Builder()
                .connectTimeout(connTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout,TimeUnit.SECONDS)
                .writeTimeout(writeTimeout,TimeUnit.SECONDS);
        mHttpClient=builder.build();
    }
    public static DownloadHelper getmInstance(){
        if (mInstance==null){
            mInstance=new DownloadHelper();
        }
        return mInstance;
    }
    public DownloadHelper buildRetrofit(String baseUrl){
        mRetrofit=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mHttpClient)
                .build();
        return this;
    }

    public <T> T createService(Class<T> serviceClass){
        return mRetrofit.create(serviceClass);
    }
}