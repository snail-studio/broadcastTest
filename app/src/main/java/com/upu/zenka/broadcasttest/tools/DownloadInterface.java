package com.upu.zenka.broadcasttest.tools;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by ThinkPad on 2018/12/21.
 */

public interface DownloadInterface {
    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String url);
}

