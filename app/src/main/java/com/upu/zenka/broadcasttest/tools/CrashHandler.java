package com.upu.zenka.broadcasttest.tools;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import android.os.Handler;

import com.upu.zenka.broadcasttest.MainActivity;
import com.upu.zenka.broadcasttest.bean.exceptionBean;
import com.upu.zenka.broadcasttest.callback.ModelCallBack;
import com.upu.zenka.broadcasttest.model.GetbroadcastModel;
import com.upu.zenka.broadcasttest.util.RetrofitUnitl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.lang.ref.WeakReference;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

import static com.upu.zenka.broadcasttest.tools.getTime.getCurtime;
import static com.upu.zenka.broadcasttest.tools.publicParam.deviceid;

/**
 * Created by ThinkPad on 2018/11/22.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String IMAGE_SDCARD_MADER ="/mnt/sdcard/";
    GetbroadcastModel getbroadcastModel=new GetbroadcastModel();
    /**
     * 系统默认UncaughtExceptionHandler
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    /**
     * context
     */
    private Context mContext;

    /**
     * 存储异常和参数信息
     */
    private Map<String,String> paramsMap = new HashMap<>();

    /**
     * 格式化时间
     */
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private String TAG = this.getClass().getSimpleName();

    private static CrashHandler mInstance;

    private CrashHandler() {

    }

    /**
     * 获取CrashHandler实例
     */
    public static synchronized CrashHandler getInstance(){
        if(null == mInstance){
            mInstance = new CrashHandler();
        }
        return mInstance;
    }

    public void init(Context context){
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为系统默认的
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * uncaughtException 回调函数
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if(!handleException(ex) && mDefaultHandler != null){//如果自己没处理交给系统处理
            mDefaultHandler.uncaughtException(thread,ex);
        }else{//自己处理
            try {//延迟3秒杀进程
                Intent intent = new Intent(mContext,MainActivity.class);
                @SuppressLint("WrongConstant") PendingIntent restartIntent = PendingIntent.getActivity(
                        mContext.getApplicationContext(), 0, intent,Intent.FLAG_ACTIVITY_NEW_TASK);
                //退出程序
                AlarmManager mgr = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, getCurtime() + 1000,
                        restartIntent); // 1秒钟后重启应用
                android.os.Process.killProcess(android.os.Process.myPid());
            } catch (Exception e) {
                Log.e(TAG, "error : ", e);
            }
            //退出程序
            AppManager.getAppManager().AppExit(mContext);
        }

    }

    /**
     * 收集错误信息.发送到服务器
     * @return 处理了该异常返回true,否则false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //添加自定义信息
        addCustomInfo();
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "程序开小差了呢..", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        //保存日志文件
        saveCrashInfo2File(ex);
        return true;
    }

    /**
     * 收集设备参数信息
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        //获取versionName,versionCode
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                paramsMap.put("versionName", versionName);
                paramsMap.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        //获取所有系统信息
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                paramsMap.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }
    private String throwable2String(Throwable ex){
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        return  writer.toString();
    }
    /**
     * 添加自定义参数
     */
    private void addCustomInfo() {

    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称,便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
//        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            sb.append(key + "=" + value + "\n");
//        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = getCurtime();
            String time = format.format(new Date());
            String fileName = "broadcast-"+timestamp+"-"+publicParam.deviceid + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = IMAGE_SDCARD_MADER + "broadcast/crash/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
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
