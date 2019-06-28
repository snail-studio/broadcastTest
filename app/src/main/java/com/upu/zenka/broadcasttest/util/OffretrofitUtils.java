package com.upu.zenka.broadcasttest.util;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ThinkPad on 2018/12/4.
 */

public class OffretrofitUtils {
    private Retrofit mRetrofit;
    private String baseUrl;
    OkHttpClient client;
    private static OffretrofitUtils mRetrofitManager;
    private OffretrofitUtils(String baseUrl,OkHttpClient client){
        this.baseUrl=baseUrl;
        this.client=client;

        initRetrofit();

    }

    //单例模式+双重锁模式  封装方法
    public static synchronized OffretrofitUtils getInstance(String baseUrl, OkHttpClient client,boolean isCreat){
        if (mRetrofitManager==null){
            mRetrofitManager=new OffretrofitUtils(baseUrl,client);
        }else{
            if (isCreat){
                mRetrofitManager=new OffretrofitUtils(baseUrl,client);
            }
        }
        return mRetrofitManager;
    }

    //实例化Retrofit请求
    private void initRetrofit(){
        mRetrofit=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    //封装泛型方法
    public <T> T setCreate(Class<T> server){
        return mRetrofit.create(server);
    }
}
