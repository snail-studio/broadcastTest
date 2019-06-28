package com.upu.zenka.broadcasttest;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.upu.zenka.broadcasttest.bean.ApResBean;
import com.upu.zenka.broadcasttest.bean.ApinfoBean;
import com.upu.zenka.broadcasttest.bean.BkbroadcastBean;
import com.upu.zenka.broadcasttest.bean.BroadcastInfo;
import com.upu.zenka.broadcasttest.bean.BroadcastmusicBean;
import com.upu.zenka.broadcasttest.bean.DownloadBean;
import com.upu.zenka.broadcasttest.bean.DownloadmusicBean;
import com.upu.zenka.broadcasttest.bean.IpinfoBean;
import com.upu.zenka.broadcasttest.bean.MusicBean;
import com.upu.zenka.broadcasttest.bean.broadcastBean;
import com.upu.zenka.broadcasttest.bean.exceptionBean;
import com.upu.zenka.broadcasttest.bean.netfaildBean;
import com.upu.zenka.broadcasttest.bean.nettestBean;
import com.upu.zenka.broadcasttest.bean.uploadBean;
import com.upu.zenka.broadcasttest.bean.vertifyBean;
import com.upu.zenka.broadcasttest.listener.DownloadListener;
import com.upu.zenka.broadcasttest.presenter.broadcastPresenter;
import com.upu.zenka.broadcasttest.tools.Getip;
import com.upu.zenka.broadcasttest.tools.geneDeviceCode;
import com.upu.zenka.broadcasttest.tools.getTime;
import com.upu.zenka.broadcasttest.tools.publicParam;
import com.upu.zenka.broadcasttest.util.BroadcastUtil;
import com.upu.zenka.broadcasttest.util.DownloadUtil;
import com.upu.zenka.broadcasttest.util.FileUtil;
import com.upu.zenka.broadcasttest.util.MusicUtil;
import com.upu.zenka.broadcasttest.util.SharedUtils;
import com.upu.zenka.broadcasttest.util.SoundsUtil;
import com.upu.zenka.broadcasttest.util.WifiUtil;
import com.upu.zenka.broadcasttest.view.MyView;

import java.io.File;
import java.io.FileInputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.upu.zenka.broadcasttest.MainHandlerConstant.AWAITOk;
import static com.upu.zenka.broadcasttest.MainHandlerConstant.INIT_SUCCESS;
import static com.upu.zenka.broadcasttest.MainHandlerConstant.OVER;
import static com.upu.zenka.broadcasttest.MainHandlerConstant.PRINT;
import static com.upu.zenka.broadcasttest.MainHandlerConstant.START;
import static com.upu.zenka.broadcasttest.MainHandlerConstant.STATUS;
import static com.upu.zenka.broadcasttest.tools.getTime.calTime;
import static com.upu.zenka.broadcasttest.tools.getTime.getCurtime;
import static com.upu.zenka.broadcasttest.tools.getTime.getLongtime;
import static com.upu.zenka.broadcasttest.tools.getTime.isAmSettime;
import static com.upu.zenka.broadcasttest.tools.getTime.isPmSettime;
import static com.upu.zenka.broadcasttest.tools.publicParam.isSeriesSpeak;
import static com.upu.zenka.broadcasttest.util.NetUtil.mContext;
import static com.upu.zenka.broadcasttest.util.SharedUtils.saveStringData;
public class MainActivity extends AppCompatActivity implements MyView.BroadcastView,MyView.MusicView{
    public static final String TAG="MainActivity";

    public static final String IMAGE_SDCARD_MADER ="/mnt/sdcard/";
    private static final String BASE_PATH= Environment.getExternalStorageDirectory()+"";
    private AudioManager audioManager;
    private Button btnWork;
    private TextView txtDeviceid;
    private TextView txtSchoolname;
    private TextView txtSchoolid;
    private TextView txtClassname;
    private TextView txtClassid;
    private TextView txtCallformate;
    private TextView txtCallTimes;
    private TextView txtNoticeTimes;
    private TextView txtDd;
    private TextView txtCallCur;
    private TextView txtStatus;
    private TextView txtWorkPattern;

    private MusicUtil musicUtil;
    private broadcastPresenter broadcastpresenter;
    public List<BroadcastInfo> broadcastInfos =new ArrayList<BroadcastInfo>();
    private BroadcastInfo curBroadcastInfo =null;
    public BroadcastInfo curBroadcast=null;
    private String speakId="2000";
    private String postfix="家长到了";
    private int broadcastTimes=3;
    private String hostname="";

    private boolean isActivate=false;
    private boolean isInitTts=false;
    private boolean netOk=false;
    private boolean isFirst=true;
    private boolean isRun=false;
    private boolean playRemind=true;
    private boolean isNative=false;
    private boolean isNativeOk=false;
    private boolean isNettestReturn=false;
    private boolean isNativeTestRetrun=false;
    private boolean isVerify=false;
    private boolean isActivating=true;
    private boolean isDownloading=true;
    private boolean isGetSpeaking=false;
    private boolean GetdataReturn=true;
    private boolean GetoffdataReturn=true;
    private boolean NettestReturn=true;
    private boolean isIniting=false;
    private boolean isGetServer=false;
    private boolean isfirstNetget=true;
    private boolean isChangeIp=false;
    private boolean isBroadcast=false;
    private boolean isFirstStart=true;
    private boolean isIdle=false;
    private boolean isReadwelcome=false;
    private boolean isUpload=false;
    private boolean isDownloadMusic=false;
    private boolean  musicOk=true;
    private boolean noBobao=true;
    private boolean playing=false;
    private boolean musicPrepare=false;
    private int nativeConnectTimes=0;
    private final MyHandler myHandler=new MyHandler(this);
    private String deviceid="";

    private List<netfaildBean> netfaildList=new ArrayList<netfaildBean>();
    private BroadcastUtil broadcastUtil=new BroadcastUtil();

    private int intervalTime=999;//ms
    private int intervalTime_netTest=29000;//ms
    private int intervalTime_verify=60;//min

    public long LastBroadcastTime=0;
    public long LastResetFailedTime=0;
    public long LastVerifyTime=0;

    private SoundPool pool;
    private Map<String, Integer> poolMap=new HashMap<String, Integer>();

    private List<String> preOffserverIpList=new ArrayList<String>();
    private List<DownloadmusicBean> downloadList=new ArrayList<DownloadmusicBean>();
    private String preOffserverIp="";
    private String curOffserverIp="";
    private String preOfffaiedIp="";
    private String ddList="";
    private String errstr="";
    private String curPlaymusic="";

    private String localip="0.0.0.0";
    private String curdownload="";

    private WifiUtil wifiUtil;
    //private DownloadUtil mDownloadUtil;

    Handler  queryHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            new TimeThread().start();
            return true;
        }
    });
    Handler  getBkmusicHandle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(netOk){
                broadcastpresenter.getBkData(publicParam.schoolId,publicParam.classId);
            }
            getBkmusicHandle.sendEmptyMessageDelayed(1,19000);
            return true;
        }
    });
    Handler  uploadExceptionHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(netOk){
                try {
                    File[] files = new File(IMAGE_SDCARD_MADER + "broadcast/crash/").listFiles();
                    if (files.length>0){

                        for (int i = 0; i < files.length; i++) {
                            File f = files[i];
                            String path = f.getPath();
                            if (getFileSize(f)==0){
                                new File(path).delete();
                                continue;
                            }
                            String exception= FileUtil.loadFromFile(f);
                            String filename=f.getName();
                            int end=filename.lastIndexOf(".");
                            filename=filename.substring(0,end);
                            String[] temp=filename.split("-");

                            long signTime=Long.parseLong(temp[1]);
                            CharSequence sysTimeStr = DateFormat.format("yyyy-MM-dd HH:mm:ss", signTime);
                            while (isGetSpeaking){
                                delay(5);
                            }
                            broadcastpresenter.sentException(temp[0],temp[2],exception,sysTimeStr.toString(),path);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                uploadExceptionHandler.sendEmptyMessageDelayed(1,59000);
            }
            return true;
        }
    });
    private int sd=0;
    private boolean isIni=false;
    Handler  initTTlHandle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (!isIni){
                if (!isInitTts){
                    sd++;
                    if (sd>10){
                        pool.play(poolMap.get("ttlfailed"), 1.0f, 1.0f, 0, 0, 1.0f);
                        sd=0;
                    }
                    speak("初始化语音",2000);
                    initTTlHandle.sendEmptyMessageDelayed(1,1000);
                }else {
                    isIni=true;
                    if(isActivate){
                        if(!publicParam.schoolname.equals("")&&!publicParam.classname.equals("")&&!isReadwelcome){
                            speak("欢迎"+publicParam.schoolname+","+publicParam.classname+"使用政凯秀智能音箱",2000);
                            isReadwelcome=true;
                        }
                    }
                    new NetTestThread().start();
                    new initThread().start();
                }
            }
            return true;
        }
    });
    Handler  downloadNativeServerinfo = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (netOk && isActivate) {
                try {
                    if (publicParam.workpattern == 3 || publicParam.workpattern == 1) {
                        while (isGetSpeaking){
                            delay(6);
                        }
                        broadcastpresenter.downloadInfo(publicParam.deviceid);
                        for(int i=0;i<11;i++){
                            Thread.sleep(1000);
                        }
                        if (publicParam.nativeServerList.size()==0){
                            downloadNativeServerinfo.sendEmptyMessageDelayed(1, 29000);
                        }
                        Log.i(TAG,"下载本地地址");
                    } else {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                downloadNativeServerinfo.sendEmptyMessageDelayed(1, 29000);
            }
            return true;
        }
    });
    public static long getFileSize(File file) {
        long size = 0;
        try {
            if (file == null) {
                return 0;
            }
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = fis.available();
            }
            return size;
        } catch (Exception e) {
            return 0;
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            //land
        }
        else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            //port
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDeviceid=(TextView)findViewById(R.id.txtDeviceid);
        txtCallformate=(TextView)findViewById(R.id.txtCallFormate);
        txtCallTimes=(TextView)findViewById(R.id.txtCallTimes);
        txtClassid=(TextView)findViewById(R.id.txtClassid);
        txtClassname=(TextView)findViewById(R.id.txtClassname);
        txtNoticeTimes=(TextView)findViewById(R.id.txtNoticeTimes);
        txtSchoolid=(TextView)findViewById(R.id.txtSchoolid);
        txtSchoolname=(TextView)findViewById(R.id.txtSchoolname);
        txtCallCur=(TextView)findViewById(R.id.txtCurCall);
        txtDd=(TextView)findViewById(R.id.txtDd);
        txtStatus=(TextView)findViewById(R.id.txtStatus);
        txtWorkPattern=(TextView)findViewById(R.id.txtWorkPattern);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,6,0);
        initFolder();

        wifiUtil=new WifiUtil(this);
        wifiUtil.closeWifiHotspot();

        publicParam.serverUrl="http://192.168.2.107:8080/";
        pool = new SoundPool(7, AudioManager.STREAM_MUSIC, 0);
        poolMap.put("remind", pool.load(this, R.raw.remind, 1));
        poolMap.put("noactive", pool.load(this, R.raw.noactive, 1));
        poolMap.put("netfailed", pool.load(this, R.raw.netfailed, 1));
        poolMap.put("ttlfailed", pool.load(this, R.raw.ttlfailed, 1));

        publicParam.callVolume=SharedUtils.getIntData(this,"callVolume",12);
        publicParam.bkmusicVolume=SharedUtils.getIntData(this,"bkmusicVolume",10);

        publicParam.workpattern=SharedUtils.getIntData(this,"workpattern",4);
        publicParam.callTimes=SharedUtils.getIntData(this,"callTimes",3);
        publicParam.noticeTimes=SharedUtils.getIntData(this,"noticeTimes",1);
        publicParam.callFormate=SharedUtils.getStringData(this,"callFormate","");
        publicParam.schoolId=SharedUtils.getStringData(this,"schoolid","0");
        publicParam.nativeServerList=getNativeServer();
        publicParam.classId_W=SharedUtils.getStringData(this,"classid_W","");
        publicParam.classId=SharedUtils.getStringData(this,"classid","");
        publicParam.schoolname=SharedUtils.getStringData(this,"schoolname","");
        publicParam.classname=SharedUtils.getStringData(this,"classname","");
        publicParam.amtimeIn=getLongtime(SharedUtils.getStringData(this,"amtimeIn","07:01:00"));
        publicParam.amtimeLeave=getLongtime(SharedUtils.getStringData(this,"amtimeLeave","09:09:00"));
        publicParam.pmtimeIn=getLongtime(SharedUtils.getStringData(this,"pmtimeIn","16:01:00"));
        publicParam.pmtimeLeave=getLongtime(SharedUtils.getStringData(this,"pmtimeLeave","19:00:00"));

        isActivate=SharedUtils.getBooleanData(MainActivity.this,"isActivate",false);

        broadcastpresenter=new broadcastPresenter(this);
        musicUtil=new MusicUtil(audioManager,this);

        localip= Getip.getHostIP();

//        broadcastpresenter.netTest(deviceid,publicParam.schoolId);
//        Log.i(TAG,"调用"+"netTest");
//        NettestReturn=false;
//        Log.i(TAG,"NettestReturn="+NettestReturn);

        initBroadcastmusic();

        deviceid= geneDeviceCode.deviceCode();
        publicParam.deviceid=deviceid;
        txtDeviceid.setText(deviceid);

        isInitTts=false;
        speak("初始化语音",2000);


        isIniting = true;
        initTTlHandle.sendEmptyMessageDelayed(1,2800);
        getBkmusicHandle.sendEmptyMessageDelayed(1,19000);


        LastVerifyTime=getCurtime();


        isRun=true;
        //initHandle.sendEmptyMessageDelayed(1,100);





        queryHandler.sendEmptyMessageDelayed(1,15000);
        downloadNativeServerinfo.sendEmptyMessageDelayed(1,4000);
        uploadExceptionHandler.sendEmptyMessageDelayed(1,59000);

        initInfo();
        Log.i(TAG,"启动完成");

    }
    public class initThread extends Thread {
        @Override
        public void run() {
            init();
        }
    }

    private void init(){
        //三种不同工作模式下，初始化完成工作不一样
        //设备是否已经激活过，
        try {
            do {
                if (isActivate) {
                    //用旧数据工作
                    isIniting=false;
                    Log.i(TAG,"已经激活isIniting="+isIniting);
                } else {
                    int sum = 0;
                    while (!netOk) {
                        sum++;
                        delay(999);
                        if (sum > 10) {
                            pool.play(poolMap.get("netfailed"), 1.0f, 1.0f, 0, 0, 1.0f);
                            Thread.sleep(2000);
                            sum = 0;
                        }
                    }
                    //激活设备+下载离线数据
                    isActivating = true;
                    while (isGetSpeaking) {
                        delay(7);
                    }
                    broadcastpresenter.verify(publicParam.deviceid);
                    sum=0;
                    while (isActivating) {
                        sum++;
                        if(sum>10){
                            broadcastpresenter.verify(publicParam.deviceid);
                        }
                        Thread.sleep(10000);
                    }
                    sum=0;
                    while (!isActivate){
                        sum++;
                        if(sum>10){
                            broadcastpresenter.verify(publicParam.deviceid);
                        }
                        Thread.sleep(10000);

                    }
                    if (publicParam.workpattern == 1 || publicParam.workpattern == 3) {
                        isDownloading = true;
                        while (isGetSpeaking) {
                            delay(8);
                        }
                        broadcastpresenter.downloadInfo(publicParam.deviceid);
                        while (isDownloading) {
                            Thread.sleep(1000);
                        }
                    }
                    //开启本地服务
                    isIniting=false;
                    Log.i(TAG,"激活完毕isIniting="+isIniting);
                }
            }while (isIniting);
            speak("系统初始化完成",2000);
            new BkMusicThread().start();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    int times=0;
    public class NetTestThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1);
                    if (isIdle) {
                        delay(4999);
                        continue;
                    }
                    if(NettestReturn) {
                        Log.i(TAG,"循环调用NettestReturn="+NettestReturn);
                        while (isGetSpeaking){
                            delay(9);
                        }
                        broadcastpresenter.netTest(deviceid, publicParam.schoolId);
                        Log.i(TAG,"调用"+"netTest");
                        NettestReturn=false;
                        Log.i(TAG,"循环调用NettestReturn="+NettestReturn);
                    }
                    Thread.sleep(intervalTime_netTest);

                } catch (InterruptedException e) {
                    NettestReturn=true;
                    e.printStackTrace();
                }
            } while (isRun);
        }
    }
    public class BkMusicThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    delay(100);
                    if (!playing&&!musicPrepare) {
                        String res = broadcastUtil.isHavePlay();
                        if (res != null) {
                            if (musicOk) {
                                if (broadcastUtil.getMusiCount() > 0) {
                                    String[] musicInfo = res.split("@");
                                    if(!broadcastUtil.have(broadcastUtil.getBroadcastmusic(Integer.parseInt(musicInfo[0]), Integer.parseInt(musicInfo[1])).getName())){
                                        continue;
                                    }
//                                    if (broadcastUtil.getBroadcastmusic(Integer.parseInt(musicInfo[0]), Integer.parseInt(musicInfo[1])).getName().equals(curdownload)) {
//                                        Log.e("MainActivity", "正在下载，不能播放");
//                                    } else {
                                        Log.e(TAG, "节目" + musicInfo[0] + "第" + musicInfo[1] + "首歌准备播放,播放位置" + musicInfo[2]);
                                        Log.e(TAG, "播放音乐" + broadcastUtil.getBroadcastmusic(Integer.parseInt(musicInfo[0]), Integer.parseInt(musicInfo[1])).getNativepath());

                                        musicPrepare=true;
                                        Log.e("MainActivity", "musicPrepare="+musicPrepare);
                                        musicUtil.play(Integer.parseInt(musicInfo[0]),
                                                Integer.parseInt(musicInfo[1]),
                                                broadcastUtil.getBroadcastmusic(Integer.parseInt(musicInfo[0]), Integer.parseInt(musicInfo[1])).getNativepath(),
                                                Integer.parseInt(musicInfo[2]),
                                                broadcastUtil.getBroadcastmusic(Integer.parseInt(musicInfo[0]), Integer.parseInt(musicInfo[1])).getVolume());
                                    curPlaymusic=musicInfo[0]+"@"+musicInfo[1]+"@"+musicInfo[3];

                                //    }
                                    //broadcastUtil.setBroadcastmusic(Integer.parseInt(musicInfo[0]));
                                }
                            }
                        }

                    }else{
                        if(playing){
                            String res=broadcastUtil.isHavePlay();
                            if(res==null){
                                musicUtil.stop();
                                playing=false;
                                musicPrepare=false;
                            }else{
                                String[] musicInfo = res.split("@");
                                res=musicInfo[0]+"@"+musicInfo[1]+"@"+musicInfo[3];
                                if(!res.equals(curPlaymusic)){
                                    musicUtil.stop();
                                    playing=false;
                                    musicPrepare=false;
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    playing=false;
                    musicPrepare=false;
                    Log.e(TAG, "播放异常");
                    e.printStackTrace();
                }
            } while (isRun);
        }
    }
    public class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
//                    if (getTime.gettime().equals("12:00:00")||getTime.gettime().equals("12:00:01")){
//                        reboot();
//                    }
                    if (isIniting||!isActivate) {
                        Log.i(TAG, "未进入轮询isIniting=" + isIniting + ";isActivate=" + isActivate);
                        Thread.sleep(1000);
                        if (!isActivate) {
                            isFirst = true;
                        }
                        continue;
                    }
                    if (!isAmSettime(getCurtime())&&!isPmSettime(getCurtime())){
                        isIdle=true;
                        Message MSG = Message.obtain();
                        MSG.what = 10011;
                        MSG.obj = "publicParam.amtimeIn="+publicParam.pmtimeIn;
                        myHandler.sendMessage(MSG);
                        if ((getCurtime()-LastVerifyTime)>300000){
                            broadcastpresenter.verify(publicParam.deviceid);
                            Log.i(TAG,"空闲时间调用verify");
                            LastVerifyTime=getCurtime();
                        }
                        delay(888);
                        continue;
                    }else{
                        LastVerifyTime=0;
                        isIdle=false;
                    }
                    if (getCurtime() - LastResetFailedTime > 180000){
                        netfaildList.clear();
                        LastResetFailedTime=getCurtime();
                    }
                    if (getCurtime() - LastBroadcastTime > 10000&&isBroadcast) {
                        //txtCallCur.setText("");
                        Message MSG = Message.obtain();
                        MSG.what = AWAITOk;
                        MSG.obj = "start";
                        myHandler.sendMessage(MSG);
                        playRemind = true;
                    }
                    if (!netOk||publicParam.workpattern==1){
                        if(!isInitTts){
                            pool.play(poolMap.get("ttlfailed"), 1.0f, 1.0f, 0, 0, 1.0f);
                            Thread.sleep(2000);
                            speak("",2000);
                            continue;
                        }
                        if (publicParam.workpattern==3){
                            if (!isNative) {
                                isNative = true;
                                speak("切换本地服务", 2000);
                            }
                        }
                        if (publicParam.workpattern==1){
                            if (!isNative) {
                                isNative = true;
                                speak("开启本地服务", 2000);
                            }
                        }
                        if (publicParam.workpattern==1||publicParam.workpattern==3) {
                            if (netfaildList.size() > 0) {
                                for (int i = 0; i < netfaildList.size(); i++) {
                                    if (netfaildList.get(i).getTimes() > (21/netfaildList.size())) {
                                        //?speak("连接" + getHostname(netfaildList.get(i).getIp()) + "失败", 2000);
                                        broadcastpresenter.downloadInfo(publicParam.deviceid);
                                        netfaildList.remove(i);
                                        i--;
                                    }
                                }
                            }
                            if (GetoffdataReturn) {
                                if (publicParam.nativeServerList.size() != 0) {
                                    getOffdata();//获取本地服务器数据
                                }else{
                                    broadcastpresenter.downloadInfo(publicParam.deviceid);
                                }
                            }
                        }
                    }
                    else {
                        if(publicParam.workpattern!=4){
                            nativeConnectTimes=0;
                            if (isFirst) {
                                speak("等待播报", 2000);
                                isFirst = false;
                            }
                            if (getCurtime() - LastBroadcastTime > 10000&&isBroadcast) {
                                //txtCallCur.setText("");
                                Message MSG = Message.obtain();
                                MSG.what = AWAITOk;
                                MSG.obj = "start";
                                myHandler.sendMessage(MSG);
                                playRemind = true;
                            }
                            intervalTime = 1000;
                            Thread.sleep(1);
                            if (GetdataReturn) {
                                while (isGetSpeaking){
                                    delay(10);
                                }
                                broadcastpresenter.getData(publicParam.schoolId, publicParam.classId);
                                GetdataReturn=false;
                                Log.i(TAG, "调用" + "getData");
                            }
                        }
                    }
                    Thread.sleep(intervalTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (isRun);
        }
    }
    @Override
    public void success(broadcastBean broadcastbean,boolean isremote){
        //editContent.setText(broadcastbean.toString());
        if (!isremote){
            isNativeOk=true;
            GetoffdataReturn=true;
            Log.i(TAG, "本地服务调用成功GetoffdataReturn=" + GetoffdataReturn);
        }else{
            GetdataReturn=true;
            Log.i(TAG, "云服务调用成功GetdataReturn=" + GetdataReturn);
        }
        if (broadcastbean.getDatas()==null){
            Log.e(TAG, "没有新播报" );
            //speak("没有新播报",1002);
        }else{
            //判断是否为多人
            Log.e(TAG, "多条播报数据" );
            for (int i=0;i<broadcastbean.getDatas().size();i++){
                if (broadcastbean.getDatas().get(i).getType()==1){
                    Log.i("qazwsxedc","播报格式"+publicParam.callFormate);
                    curBroadcastInfo=new BroadcastInfo(publicParam.callFormate.replace("name",broadcastbean.getDatas().get(i).getContent())+","+broadcastbean.getDatas().get(i).getSuffix(),publicParam.callTimes);
                }else{
                    Log.i("qazwsxedc","播报格式2"+publicParam.callFormate);
                    curBroadcastInfo=new BroadcastInfo(broadcastbean.getDatas().get(i).getContent(),publicParam.noticeTimes);
                }
                broadcastInfos.add(curBroadcastInfo);
            }
            //if (broadcastInfos.size()==1){
                if (speakId.equals("2000")) {
                    Message MSG = Message.obtain();
                    MSG.what = START;
                    MSG.obj = "start";
                    myHandler.sendMessage(MSG);
                }
            //}
        }
    }
    @Override
    public void failed(int  code){
        Toast.makeText(this,"网络连接失败"+code,Toast.LENGTH_SHORT).show();
        //if (code==10001){
        if (code==10006){
            Log.i(TAG,"下载本地服务信息失败");
            uploadExceptionHandler.sendEmptyMessageDelayed(1,29000);
        }
        if (code==10004){
            GetdataReturn=true;
            Log.i(TAG,"GetdataReturn="+GetdataReturn);
        }
        if (code==10003){
            addOfffailed(preOffserverIp);
            GetoffdataReturn=true;
            Log.i(TAG,"本地调用失败GetoffdataReturn="+GetoffdataReturn);
            nativeConnectTimes++;
            Log.i(TAG,"本地网络请求失败"+code);
            preOfffaiedIp=preOffserverIp;
        }
        if(code==10001){
            isfirstNetget=false;
            NettestReturn=true;
            Log.i(TAG,"NettestReturn="+NettestReturn);
            netOk=false;
            Log.i(TAG,"错误代码"+code);
           // Toast.makeText(this,"网络连接失败",Toast.LENGTH_SHORT).show();
        }
        if (code==10002){
            ;
        }
        if (code==10008){
            localip= Getip.getHostIP();
            broadcastpresenter.upload(deviceid,localip,publicParam.Version);
        }
        if (isInitTts){
            if (!isNative){
                //speak("网络连接失败",11111);
                Log.i(TAG,"添加网络连接失败语音"+code);
            }
        }

       // }
    }
    @Override
    public void downloadInfo(DownloadBean downloadBean) {
        if (downloadBean.getCode()==1000){
            //下载服务器地址成功
            Log.i(TAG,"下载本地地址成功");
            if (downloadBean.getData()!=null){
                publicParam.nativeServerList.clear();
                for (int i=0;i<downloadBean.getData().getIpinfo().size();i++){
                    publicParam.nativeServerList.add(downloadBean.getData().getIpinfo().get(i));
                    Log.i(TAG,"下载地址："+downloadBean.getData().getIpinfo().get(i).getIp());
                }
                saveNativeServer(publicParam.nativeServerList);
            }
            isDownloading=false;
            isGetServer=true;
        }else{
            Log.i(TAG,"下载地址为空");
        }
    }
    @Override
    public void upload(uploadBean uploadbean){
        if (uploadbean.getCode()==1000){
            isUpload=true;
        }
    }
    @Override
    public void verify(vertifyBean verifybean){
        if (verifybean.getCode()==1000){
            errstr="";

            if(verifybean.getData().getApinfo()!=null){
                ManageAp(verifybean.getData().getApinfo());
            }
            //验证通过
            saveStringData(this,"schoolid",verifybean.getData().getSchoolid());
            publicParam.schoolId=verifybean.getData().getSchoolid();
            publicParam.schoolname=verifybean.getData().getSchoolname();
            saveStringData(this,"schoolname",verifybean.getData().getSchoolname());
            if (verifybean.getData().getClassid()!=null){
                if(verifybean.getData().getClassid().indexOf("-")!=-1){
                    String [] classes=verifybean.getData().getClassid().split("-");
                    SharedUtils.saveStringData(this,"classid_W",classes[1]);
                    publicParam.classId_W=classes[1];

                    SharedUtils.saveStringData(this,"classid",classes[0]);
                    publicParam.classId=classes[0];
                    publicParam.classname=verifybean.getData().getClassname();
                    saveStringData(this,"classname",verifybean.getData().getClassname());
                }else{
                    saveStringData(this,"classid",verifybean.getData().getClassid());
                    SharedUtils.saveStringData(this,"classid_W","");
                    publicParam.classId=verifybean.getData().getClassid();
                    publicParam.classname=verifybean.getData().getClassname();
                    saveStringData(this,"classname",verifybean.getData().getClassname());
                }

            }else {
                saveStringData(this,"classid","");
                saveStringData(this,"classid_W","");
                saveStringData(this,"classname","");

                publicParam.classId="";
                publicParam.classId_W="";
                publicParam.classname="";
            }
            if (verifybean.getData().getConfiguration()!=null){
                SharedUtils.saveIntData(this,"callTimes",verifybean.getData().getConfiguration().getCallTimes());
                publicParam.callTimes=verifybean.getData().getConfiguration().getCallTimes();
                SharedUtils.saveIntData(this,"noticeTimes",verifybean.getData().getConfiguration().getNoticeTimes());
                publicParam.noticeTimes=verifybean.getData().getConfiguration().getNoticeTimes();
                saveStringData(this,"callFormate",verifybean.getData().getConfiguration().getCallFormate());
                publicParam.callFormate=verifybean.getData().getConfiguration().getCallFormate();
                SharedUtils.saveIntData(this,"workpattern",verifybean.getData().getConfiguration().getWorkpattern());
                publicParam.workpattern=verifybean.getData().getConfiguration().getWorkpattern();
                SharedUtils.saveIntData(this,"workpattern",verifybean.getData().getConfiguration().getWorkpattern());

                publicParam.callVolume=verifybean.getData().getConfiguration().getVolume();
                SharedUtils.saveIntData(this,"callVolume",verifybean.getData().getConfiguration().getVolume());

                saveStringData(this,"amtimeIn",verifybean.getData().getConfiguration().getWorktime().getAmtimein());
                publicParam.amtimeIn=getLongtime(SharedUtils.getStringData(this,"amtimeIn","07:01:00"));
                saveStringData(this,"amtimeLeave",verifybean.getData().getConfiguration().getWorktime().getAmtimeleave());
                publicParam.amtimeLeave=getLongtime(SharedUtils.getStringData(this,"amtimeLeave","09:09:00"));
                saveStringData(this,"pmtimeIn",verifybean.getData().getConfiguration().getWorktime().getPmtimein());
                publicParam.pmtimeIn=getLongtime(SharedUtils.getStringData(this,"pmtimeIn","16:01:00"));
                saveStringData(this,"pmtimeLeave",verifybean.getData().getConfiguration().getWorktime().getPmtimeleave());
                publicParam.pmtimeLeave=getLongtime(SharedUtils.getStringData(this,"pmtimeLeave","19:00:00"));

            }
            SharedUtils.saveBooleanData(this,"isActivate",true);
            isActivate=true;
            isActivating=false;
            txtStatus.setText("运行");
            if(isFirstStart&&!isReadwelcome){
                Log.i(TAG,"publicParam.schoolname="+publicParam.schoolname+";;publicParam.classname="+publicParam.classname);
                speak("欢迎"+publicParam.schoolname+","+publicParam.classname+"使用政凯秀智能音箱",2000);
                isReadwelcome=true;
                isFirstStart=false;
            }
            initInfo();
            //broadcastpresenter.getBkData(publicParam.schoolId);
            //broadcastpresenter.getBkData("37");
        }else{
            //验证失败
//            if(isActivating){
//                speak(verifybean.getMessage(),2000);
//            }
            errstr=verifybean.getMessage();
            txtStatus.setText(verifybean.getMessage());
            SharedUtils.saveBooleanData(this,"isActivate",false);
            isActivate=false;
            isActivating=false;
            if (errstr.equals("设备停用")){
                speak("设备停用，请联系管理员",2000);
            }else if(errstr.equals("设备未授权")){
                speak("设备未授权，请联系经销商",2000);
            }else if(errstr.equals("设备未启用")){
                speak("设备未启用，请联系管理员",2000);
            }
            else if(errstr.equals("设备已删除")){
                speak("设备已删除，请联系管理员",2000);
            }
            else{
                ;
            }
        }
    }
    @Override
    public void bkBroadcast(BkbroadcastBean bkbroadcastBean){
        if (bkbroadcastBean.getCode()==1000){
            publicParam.OFFSET=calTime(bkbroadcastBean.getServerTime())-System.currentTimeMillis();
            broadcastUtil.clear();
            if (bkbroadcastBean.getData()==null){
                publicParam.OFFSET=calTime(bkbroadcastBean.getServerTime())-System.currentTimeMillis();
                broadcastUtil.clear();
                return;
            }
            for (int i=0;i<bkbroadcastBean.getData().size();i++){
                broadcastUtil.addBroadcast(bkbroadcastBean.getData().get(i));
                if (downloadList.size()<=0){
                    addDownloadmust(bkbroadcastBean.getData().get(i).getMusicList());
                    Log.e(TAG,"返回调用下载");
                    download(getUrl());

                }else{
                    for (int j=0;j<downloadList.size();j++){
                        if (!downloadList.get(j).isDownload()){
                            addDownloadmust(bkbroadcastBean.getData().get(i).getMusicList());
                            break;
                        }
                        if(j==(downloadList.size()-1)){
                            addDownloadmust(bkbroadcastBean.getData().get(i).getMusicList());
                            for (int k=0;k<downloadList.size();k++){
                                if(!downloadList.get(k).isDownload()){
                                    if (!isDownloadMusic){
                                        Log.e(TAG,"二次返回调用下载");
                                        download(getUrl());
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    //addDownloadmust(bkbroadcastBean.getData().get(i).getMusicList());
                }
            }

            //获取到地址后，开始异步下载
        }else{
            publicParam.OFFSET=calTime(bkbroadcastBean.getServerTime())-System.currentTimeMillis();
            broadcastUtil.clear();
        }
    }
    @Override
    public void getApinfo(ApinfoBean apinfoBean){
        ;
    }
    private void initInfo(){
        txtSchoolname.setText(publicParam.schoolname);
        txtSchoolid.setText(publicParam.schoolId);
        txtClassname.setText(publicParam.classname);
        txtClassid.setText(publicParam.classId);
        txtCallTimes.setText(publicParam.callTimes+"");
        txtCallformate.setText(publicParam.callFormate);
        txtNoticeTimes.setText(publicParam.noticeTimes+"");
        switch (publicParam.workpattern){
            case 1:
                txtWorkPattern.setText("本地模式");
                break;
            case 2:
                txtWorkPattern.setText("在线模式");
                break;
            case 3:
                txtWorkPattern.setText("混合模式");
                break;
            case 4:
                txtWorkPattern.setText("单广播模式");
                break;
            default:
                break;
        }
    }
    @Override
    public void netTest(nettestBean nettestbean){

        if(nettestbean.getCode()==1000){
            NettestReturn=true;
            netOk=true;
            if (publicParam.workpattern==3) {
                if (isNative) {
                    speak("切换云服务", 11111);
                    Log.i(TAG, "切换云服务");
                    isNative = false;
                }
            }
            Toast.makeText(this,"网络连接正常",Toast.LENGTH_SHORT).show();
            while (isGetSpeaking&&!isfirstNetget){
                Log.i(TAG,"isGetSpeaking="+isGetSpeaking+";isfirstNetget="+isfirstNetget);
                delay(12);
            }
            isfirstNetget=false;
            if (!isUpload){
                localip=Getip.getHostIP();
                broadcastpresenter.upload(deviceid,localip,publicParam.Version);
            }
            if(!isIniting){
                broadcastpresenter.verify(deviceid);
                Log.i(TAG,"nettest调用verify");
            }
        }else{
            NettestReturn=true;
        }
    }
    @Override
    public void sentException(exceptionBean exceptionbean,String filepath) {
        try {
            new File(filepath).delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void playFaild(String code){
        playing=false;
        musicPrepare=false;
        Log.e("MainActivity", "播放失败musicPrepare="+musicPrepare+"失败位置"+code);
    }
    @Override
    public void  keepup(){
        playing=true;;
    }
    @Override
    public void  play(){
        playing=true;
        musicPrepare=false;
        Log.e("MainActivity", "开始播放musicPrepare="+musicPrepare);
    }
    @Override
    public void  stop(){
        ;
    }
    @Override
    public void  completion(int programIndex,int musicIndex){
        //broadcastUtil.setMusicPlayed(programIndex,musicIndex);
        Log.e(TAG,"节目"+programIndex+"第"+musicIndex+"首歌播放完毕");
        musicUtil.release();
        musicPrepare=false;
        playing=false;
    }
    @Override
    public void  pause(){
        ;
    }
    @Override
    public void  error(){
        ;
    }
    Random rand = new Random();
    private void speak(String text,int index){
        musicUtil.pause();
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,publicParam.callVolume,0);
        noBobao=false;
        isGetSpeaking=true;
        Log.i(TAG,"isGetSpeaking=true");
        int result= SoundsUtil.speak(text,index+"",this,myHandler);
        isGetSpeaking=false;
//        int result= SoundsUtil.getsynthesizer(getApplicationContext()).speak(text,index+"");
        checkResult(result,"speak");
    }
    private void checkResult(int result, String method) {
        if (result != 0) {
            Toast.makeText(MainActivity.this,"error code :" + result + " method:" + method + ", 错误码文档:http://yuyin.baidu.com/docs/tts/122 ",Toast.LENGTH_LONG).show();
        }
    }

    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivty;

        public MyHandler(MainActivity mActivty) {
            this.mActivty = new WeakReference<MainActivity>(mActivty);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = mActivty.get();
            if (activity != null) {
                switch (msg.what) {
                    case 10001:
                        //activity.txtStatus.setText(msg.obj.toString())
                        if (!activity.isDownloadMusic){
                            Log.e(TAG,"下载完成调用下载"+msg.obj.toString());
                            activity.download(activity.getUrl());
                        }
                        break;
                    case STATUS:
                        activity.txtStatus.setText(msg.obj.toString());
                        break;
                    case AWAITOk:
                        activity.txtCallCur.setText("");
                        activity.isBroadcast=false;
                        break;
                    case PRINT:
                        String res=msg.obj.toString().substring(0,2);
                        switch (res){
                            case "00":
                                Toast.makeText(activity,"开始合成",Toast.LENGTH_LONG).show();
                                break;
                            case "01":
                                Toast.makeText(activity,"结束合成",Toast.LENGTH_LONG).show();
                                break;
                            case "02":
                                Toast.makeText(activity,"开始播放",Toast.LENGTH_LONG).show();
                                break;
                            case "03":
                                Toast.makeText(activity,"开始播放1111",Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;
                        }
//                        Toast.makeText(activity,msg.obj.toString(),Toast.LENGTH_LONG).show();
                        break;
                    case START:
                    case OVER:
                        Log.e(TAG,"当然播放完毕"+activity.speakId+"");
                        Log.i(TAG,"isGetSpeaking=false");
                        try{
                            if(!activity.speakId.equals("2000")){
                                if (!activity.speakId.equals(msg.obj.toString())){
                                    //activity.displayWaitList();
                                    return;
                                }
                            }
                            Thread.sleep(500);
                            activity.curBroadcast=activity.getBroadcastInfo();
                            if (activity.curBroadcast!=null){
                                activity.txtCallCur.setText(activity.curBroadcast.getContent());

                                activity.broadCast(activity.curBroadcast);
                                activity.LastBroadcastTime=getCurtime();
                                activity.isBroadcast=true;
                                activity.curBroadcast.broad();
                                activity.playRemind=false;
                                if (activity.curBroadcast.getTimes()==0){
                                    activity.broadcastInfos.remove(activity.curBroadcast);
                                }
                                //activity.displayWaitList();

                            }else{
                                activity.speakId="2000";
                                activity.noBobao=true;
                            }
                            activity.displayWaitList();
                        }catch (Exception e){
                            activity.speakId="2000";
                            activity.noBobao=true;
                            e.printStackTrace();
                        }
                        break;
                    case INIT_SUCCESS:
                        activity.isInitTts=true;
                        activity.isGetSpeaking=false;
                        Log.i(TAG,"初始化完成isGetSpeaking=false");
                        activity.speak("语音初始化成功",3000);
                        break;
                    default:
                        break;
                }
            }
        }
    }
    private void displayWaitList(){
        ddList="";
        for (int i=0;i<broadcastInfos.size();i++){
            ddList+=broadcastInfos.get(i).getContent()+";";
        }
        txtDd.setText(ddList);
        if (noBobao){
            musicOk=true;
            Log.e(TAG,"继续播放");
            musicUtil.keepup();
        }
    }
    public void broadCast(BroadcastInfo broadcastInfo) {
        musicUtil.pause();
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,publicParam.callVolume,0);
        noBobao=false;
        String index = rand.nextInt(100) + "";
        speakId = index;
        try {
            if (playRemind) {
                pool.play(poolMap.get("remind"), 1.0f, 1.0f, 0, 0, 1.0f);
                Thread.sleep(3000);
            }
            Log.e(TAG,"发送播报");
            SoundsUtil.speak(broadcastInfo.getContent(), index, this, myHandler);

        } catch (Exception e) {
            SoundsUtil.speak(broadcastInfo.getContent(), index, this, myHandler);
            e.printStackTrace();
        }
    }
    public BroadcastInfo getBroadcastInfo(){
        BroadcastInfo broadcastInfo=null;
        int sum=broadcastInfos.size();
        int indexSum=0;
        if (sum>0){
            if (!isSeriesSpeak){
                if (sum>1){
                    if (sum>3){
                        indexSum=3;
                    }
                    else {
                        indexSum=sum;
                    }
                    broadcastInfo=broadcastInfos.get(0);
                    for (int i=0;i<indexSum;i++){
                        if ((i+1)>=indexSum)
                            break;
                        int res=broadcastInfos.get(i+1).getTimes()-broadcastInfos.get(i).getTimes();
                        switch (res){
                            case 0:
                                broadcastInfo = broadcastInfos.get(0);
                                break;
                            case 1:
                                broadcastInfo=broadcastInfos.get(i+1);
                                return broadcastInfo;
                            default:
                                broadcastInfo = broadcastInfos.get(0);
                                return broadcastInfo;
                        }
                    }
                }
                else {
                    broadcastInfo=broadcastInfos.get(0);
                }
            }
            else {
                broadcastInfo=broadcastInfos.get(0);
            }
        }
        return broadcastInfo;
    }

    @Override
    protected void onDestroy() {
        isRun=false;
        SoundsUtil.release();
        if(pool!=null){
            pool.release();
            pool=null;
        }
        super.onDestroy();
    }
    private int serveripkong=0;
    private void getOffdata(){
        //for (int i=0;i<publicParam.nativeServerList.size();i++){
            if (GetoffdataReturn){
                Log.i(TAG,"GetoffdataReturn="+GetoffdataReturn);
                curOffserverIp=getCurServerIp();
                if(curOffserverIp==""){
                    if (serveripkong>30){
                        speak("本地服务地址为空",2000);
                        broadcastpresenter.downloadInfo(publicParam.deviceid);
                        serveripkong=0;
                    }else{
                        serveripkong++;
                    }
                    return;
                }
                if(preOffserverIp.equals(curOffserverIp)){
                    isChangeIp=false;
                }else{
                    isChangeIp=true;
                }
                Log.i(TAG, "调用" + "getOffData,地址ip="+curOffserverIp);
                GetoffdataReturn=false;
                Log.i(TAG,"GetoffdataReturn="+GetoffdataReturn);
                if (publicParam.nativeServerList.size()>1){
                    while (isGetSpeaking){
                        delay(13);
                    }
                    if (publicParam.classId_W.equals("")){
                        Log.i(TAG,"1");
                        broadcastpresenter.getOffData(publicParam.schoolId, publicParam.classId,curOffserverIp,isChangeIp);
                    }else{
                        Log.i(TAG,"2");
                        broadcastpresenter.getOffData(publicParam.schoolId, publicParam.classId_W,curOffserverIp,isChangeIp);
                    }

                }else{
                    while (isGetSpeaking){
                        delay(14);
                    }

                    if (publicParam.classId_W.equals("")){
                        Log.i(TAG,"3");
                        broadcastpresenter.getOffData(publicParam.schoolId, publicParam.classId,curOffserverIp,isChangeIp);
                    }else{
                        Log.i(TAG,"4");
                        broadcastpresenter.getOffData(publicParam.schoolId, publicParam.classId_W,curOffserverIp,isChangeIp);
                    }
                }
                preOffserverIp=curOffserverIp;
//                if (!getCurServerIp().equals(preOffserverIp)){
//                    broadcastpresenter.getOffData(publicParam.schoolId, publicParam.classId,publicParam.nativeServerList.get(i),true);
//                }else{
//                    broadcastpresenter.getOffData(publicParam.schoolId, publicParam.classId,publicParam.nativeServerList.get(i),false);
//                }


            }
       // }
    }
    private void delay(int interval){
        try{
            //Log.i(TAG,"调用"+"延时"+interval);
            Thread.sleep(interval);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private String strIp="";
    private String getCurServerIp() {
        if (publicParam.nativeServerList.size()==0){
            return  "";
        }
        if (publicParam.nativeServerList.size() == 1) {
            strIp=publicParam.nativeServerList.get(0).getIp();
        }
        for (int i = 0; i < publicParam.nativeServerList.size(); i++) {
            if (preOffserverIp.equals(publicParam.nativeServerList.get(i).getIp())) {
                if (i < (publicParam.nativeServerList.size() - 1)) {
                    strIp=publicParam.nativeServerList.get(i + 1).getIp();
                    break;
                } else {
                    strIp=publicParam.nativeServerList.get(0).getIp();
                    break;
                }
            }else{
                if (i==(publicParam.nativeServerList.size()-1)){
                    strIp=publicParam.nativeServerList.get(0).getIp();
                }
            }
        }
        return strIp;
    }
    private List<IpinfoBean> getNativeServer(){
        List<IpinfoBean> list=new ArrayList<IpinfoBean>();
        IpinfoBean ipinfoBean=null;
        for (int i=0;i<10;i++){
            if (!SharedUtils.getStringData(this,"serverip"+i,"0").equals("0")){
                Log.i(TAG,"index="+i+";serverip="+SharedUtils.getStringData(this,"serverip"+i,"0"));
                ipinfoBean=new IpinfoBean(SharedUtils.getStringData(this,"serverip"+i,"0"),SharedUtils.getStringData(this,"serverhostname"+i,"0"));
                list.add(ipinfoBean);
            }
        }
        return list;
    }
    private void saveNativeServer(List<IpinfoBean> list){
        for (int i=0;i<10;i++){
            saveStringData(this,"serverip"+i,"0");
            saveStringData(this,"serverhostname"+i,"0");
        }
        for (int i=0;i<list.size();i++){
            saveStringData(this,"serverip"+i,list.get(i).getIp());
            saveStringData(this,"serverhostname"+i,list.get(i).getIp());
        }
    }
    private void addOfffailed(String ip){
        if (netfaildList.size()>0){
            for (int i=0;i<netfaildList.size();i++){
                if (netfaildList.get(i).getIp().equals(ip)){
                    netfaildList.get(i).setTimes();
                    break;
                }
                if (i==(netfaildList.size()-1)){
                    netfaildList.add(new netfaildBean(ip,0));
                    break;
                }
            }
        }else{
            netfaildList.add(new netfaildBean(ip,0));
        }

    }
    private String getHostname(String ip){
        hostname="";
        for (int i=0;i<publicParam.nativeServerList.size();i++){
            if (publicParam.nativeServerList.get(i).getIp().equals(ip)){
                hostname=publicParam.nativeServerList.get(i).getHostname();
            }
        }
        return hostname;
    }
    private MusicBean getUrl(){
        MusicBean music=null;
        for (int i=0;i<downloadList.size();i++){
            if (!downloadList.get(i).isDownload()){
                music= downloadList.get(i).getMusicBean();
                break;
            }
        }
        return music;
    }
    int  indexnum=1000;
    private DownloadmusicBean getdownload(String filename){
        DownloadmusicBean downloadmusic=null;
        for (int i=0;i<downloadList.size();i++){
            if (downloadList.get(i).getMusicBean().getName().equals(filename)&&!downloadList.get(i).isDownload()){
                downloadList.get(i).setDownload(true);
                break;
            }
        }
        return downloadmusic;
    }
    private void download(MusicBean music){
        if (music==null) {;
            return;
        }
        isDownloadMusic=true;
        List<MusicBean> musics=new ArrayList<MusicBean>();
        musics.add(music);

        DownloadUtil mDownloadUtil=new DownloadUtil();
        mDownloadUtil.downloadFile(musics, new DownloadListener() {
            @Override
            public void onStart(String filename) {
                curdownload=filename;
                Log.e(TAG,filename+"开始下载");
            }

            @Override
            public void onProgress(String filename,int currentLength) {
               // Log.e(TAG,filename+"已下载"+currentLength);
            }

            @Override
            public void onFinish(String filename,String oldpath,String newpath) {
                curdownload="";

                if (!oldpath.equals("")){
                    File oldfile=new File(oldpath);
                    File newfile=new File(newpath);
                    if(oldfile.renameTo(newfile)){
                            getdownload(filename);
                        broadcastUtil.addMusicReadyList(filename);
                    }
                }else{
                        getdownload(filename);
                    broadcastUtil.addMusicReadyList(filename);
                }
                indexnum++;
                Log.e(TAG,filename+"下载完成"+indexnum);
//                if(downloadList.size()==0){
                   // Log.e(TAG,"下载完成");
                isDownloadMusic=false;
                    Message MSG = Message.obtain();
                    MSG.what = 10001;
                    MSG.obj = "1111下载完成"+indexnum;
                    myHandler.sendMessage(MSG);
//                }else{
////                    Message MSG = Message.obtain();
////                    MSG.what = 10002;
////                    MSG.obj = "继续下载";
////                    myHandler.sendMessage(MSG);
//
//                }
            }

            @Override
            public void onFailure(File file,String erroInfo) {
                String path = file.getPath();
                new File(path).delete();
                isDownloadMusic=false;
                Log.e(TAG,"下载异常："+erroInfo);
                if(downloadList.size()!=0){
                    Message MSG = Message.obtain();
                    MSG.what = 10001;
                    MSG.obj = "下载异常";
                    myHandler.sendMessage(MSG);
                    //download(getUrl());
                }
                curdownload="";
            }
        });
    }
    private void initBroadcastmusic(){
        BroadcastmusicBean broadcastmusicBean=new BroadcastmusicBean();
        List<MusicBean> musicList=new ArrayList<MusicBean>();
        musicList.add(new MusicBean(Environment.getExternalStorageDirectory()+"/mymusic/1.mp3",303,false));
        musicList.add(new MusicBean(Environment.getExternalStorageDirectory()+"/mymusic/2.mp3",286,false));
        musicList.add(new MusicBean(Environment.getExternalStorageDirectory()+"/mymusic/3.mp3",196,false));
        broadcastmusicBean.setPlayed(false);
        broadcastmusicBean.setTimes(2);
        broadcastmusicBean.setTotalLength(785);
        broadcastmusicBean.setMusicList(musicList);
        broadcastmusicBean.setPlaytime("09:20:00");
        //broadcastUtil.addBroadcast(broadcastmusicBean);
    }

    private void initFolder(){
        FileUtil.makeDir(BASE_PATH+"/voicebox");
        FileUtil.makeDir(BASE_PATH+"/voicebox/bkmusic/");
        FileUtil.makeDir(BASE_PATH+"/voicebox/config");
        FileUtil.makeDir(BASE_PATH+"/voicebox/log");
    }
    private void addDownloadmust(List<MusicBean> list){
        boolean isSame=false;

        for (int i=0;i<list.size();i++){
            isSame=false;
            for (int j=0;j<downloadList.size();j++){
                if (downloadList.get(j).getMusicBean().getPath().equals(list.get(i).getPath())){
                    isSame=true;
                    break;
                }
            }
            if (!isSame){
                downloadList.add(new DownloadmusicBean(list.get(i)));
            }

        }
    }

    private void ManageAp(ApResBean apResBean){
        if (apResBean.getEnabled()==1){
            wifiUtil.createWifiHotspot(apResBean.getSsid(),apResBean.getPsw(),apResBean.getSafetype());
        }else{
            wifiUtil.closeWifiHotspot();
        }
    }

    private void reboot(){
        Intent intent = new Intent(mContext,MainActivity.class);
        @SuppressLint("WrongConstant") PendingIntent restartIntent = PendingIntent.getActivity(
                mContext.getApplicationContext(), 0, intent,Intent.FLAG_ACTIVITY_NEW_TASK);
        //退出程序
        AlarmManager mgr = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, getCurtime() + 2000,
                restartIntent); // 1秒钟后重启应用
        System.exit(0);
    }
}
