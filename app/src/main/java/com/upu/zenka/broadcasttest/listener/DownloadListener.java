package com.upu.zenka.broadcasttest.listener;

import java.io.File;

/**
 * Created by ThinkPad on 2018/12/21.
 */

public interface DownloadListener {
    void onStart(String filename);
    void onProgress(String filename,int currentLength);
    void onFinish(String filename,String oldpath,String newpath);
    void onFailure(File file, String erroInfo);
}

