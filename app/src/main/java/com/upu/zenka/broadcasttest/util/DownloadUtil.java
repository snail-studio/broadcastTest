package com.upu.zenka.broadcasttest.util;

import android.os.Environment;
import android.util.Log;

import com.upu.zenka.broadcasttest.bean.MusicBean;
import com.upu.zenka.broadcasttest.helper.DownloadHelper;
import com.upu.zenka.broadcasttest.listener.DownloadListener;
import com.upu.zenka.broadcasttest.tools.DownloadInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ThinkPad on 2018/12/21.
 */

public class DownloadUtil {
    private static final String TAG="DownloadUtil";
    private static final String SAVE_PATH= Environment.getExternalStorageDirectory()+"/upudownload";
    private static final String BASE_PATH=Environment.getExternalStorageDirectory()+"/voicebox/bkmusic/";
    //protected DownloadInterface mApi;
    //private Call<ResponseBody> mCall;
    private File mFile;
    //private Thread mThread;
    private String mDownloadPath;
    public DownloadUtil(){
//        if (mApi==null){
//            mApi=ApiHelper.getmInstance().buildRetrofit("http://www.baidu.com")
//                    .createService(DownloadInterface.class);
//        }
    }
    private String filaname="";
    private String newpath="";
    private String oldpath="";
    public void downloadFile(List<MusicBean> musicList, final DownloadListener downloadListener){
        for (int j = 0; j < musicList.size(); j++) {
            filaname=musicList.get(j).getName();
            String name = musicList.get(j).getName();
            String downloadPath=musicList.get(j).getPath();
            String nativePath=BASE_PATH+name;
            DownloadInterface mApi = DownloadHelper.getmInstance().buildRetrofit("http://www.baidu.com")
                    .createService(DownloadInterface.class);
            if (mApi == null) {
                Log.e(TAG, "下载接口为空");
                return;
            }
            mFile = new File(BASE_PATH+musicList.get(j).getName());
            newpath=BASE_PATH+musicList.get(j).getName();
            oldpath=BASE_PATH+"tempmusic.mp3";
            Call<ResponseBody> mCall = mApi.downloadFile(musicList.get(j).getPath());
            Log.e("MainActivity", "创建文件"+BASE_PATH+musicList.get(j).getName());
            if (!FileUtils.isFileExists(mFile)) {
                mFile = new File(oldpath);
                if (FileUtils.createOrExistsFile(mFile)){
                    Log.e("MainActivity", "准备下载文件" + musicList.get(j).getPath());



                    mCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                            Thread mThread = new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    Log.e("MainActivity", "准备写文件" + filaname);
                                    writeFile2Disk(response, mFile, filaname,oldpath,newpath, downloadListener);
                                }
                            };
                            mThread.start();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            downloadListener.onFailure(mFile, "网络错误!");
                        }
                    });
                }
            }else {
                Log.e("MainActivity","已存在，下载完成");
                downloadListener.onFinish(filaname,"","");
            }
        }
    }
    private void writeFile2Disk(Response<ResponseBody> response, File file, String filaname,String oldpath,String newpath,DownloadListener downloadListener) {
        OutputStream os = null;
        InputStream is = null;
        try {
            is = response.body().byteStream();
            String sss = response.raw().request().url().url().toString();
            String filename1 = "";

            File file1 = new File(BASE_PATH+filaname);

            downloadListener.onStart(filaname);
            long curentLength = 0;

            if (response.body() == null) {
                downloadListener.onFailure(file, "资源错误");
                return;
            }

            long totalLength = response.body().contentLength();

            os = new FileOutputStream(file);
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                curentLength += len;
                Log.e(TAG, "当前进度：" + curentLength);
                downloadListener.onProgress(filaname, (int) (100 * curentLength / totalLength));
                if ((int) (100 * curentLength / totalLength) == 100) {
                    Log.e("MainActivity","文件真正下载完成");
                    downloadListener.onFinish(filaname,oldpath,newpath);
                }
            }
        } catch (FileNotFoundException e) {
            downloadListener.onFailure(file, "未找到文件");
            e.printStackTrace();
        } catch (IOException e) {
            downloadListener.onFailure(file, "IO错误");
            e.printStackTrace();
        } catch (Exception e){
            downloadListener.onFailure(file, "其他错误");
            e.printStackTrace();
        }
        finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
