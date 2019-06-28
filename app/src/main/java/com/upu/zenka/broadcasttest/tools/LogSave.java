package com.upu.zenka.broadcasttest.tools;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.upu.zenka.broadcasttest.tools.getTime.getCurtime;

/**
 * Created by ThinkPad on 2019/6/28.
 */

public class LogSave {
    public static final String TAG="LogSave";
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    public static String saveLog(String log) {
        try {
            StringBuffer sb = new StringBuffer();
            long timestamp = getCurtime();
            String time = format.format(new Date());
            String fileName = "spk-"+time+ ".log";
            sb.append(format2.format(new Date())+":"+getCurtime()+" "+log+"\r\n");
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = Environment.getExternalStorageDirectory() + "/broadcast/crash/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName,true);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }
}
