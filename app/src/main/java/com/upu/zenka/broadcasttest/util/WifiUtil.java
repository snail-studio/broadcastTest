package com.upu.zenka.broadcasttest.util;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ThinkPad on 2019/1/6.
 */

public class WifiUtil {
    public static final String TAG="WifiUtil";
    private WifiManager wifiManager;
    private String pressid="";
    private String prepsw="";
    private WifiConfiguration config;
    private boolean wifiEnabled=false;
    public WifiUtil(Context context){
        wifiManager=(WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiEnabled=true;
    }
    private WifiConfiguration setWificonfig(String ssid,String psw,int saftType){
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = ssid;
        config.preSharedKey = psw;
        config.hiddenSSID = false;
        config.allowedAuthAlgorithms
                .set(WifiConfiguration.AuthAlgorithm.OPEN);//开放系统认证
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedKeyManagement.set(saftType);
        config.allowedPairwiseCiphers
                .set(WifiConfiguration.PairwiseCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedPairwiseCiphers
                .set(WifiConfiguration.PairwiseCipher.CCMP);
        config.status = WifiConfiguration.Status.ENABLED;
        return config;
    }
    public void createWifiHotspot(String ssid,String psw,int saftType) {
        if (wifiManager.isWifiEnabled()) {
            //如果wifi处于打开状态，则关闭wifi,
            wifiManager.setWifiEnabled(false);
        }
        if (!prepsw.equals(psw)||!pressid.equals(ssid)){
            closeWifiHotspot();
            config=setWificonfig(ssid,psw,saftType);
        }else {
            if (wifiEnabled){
                return;
            }
        }
        //通过反射调用设置热点
        try {
            Method method = wifiManager.getClass().getMethod(
                    "setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
            boolean enable = (Boolean) method.invoke(wifiManager, config, true);
            if (enable) {
                pressid=ssid;
                prepsw=psw;
                wifiEnabled=true;
                Log.e(TAG,"热点已开启");
            } else {
                Log.e(TAG,"热点创建失败");
            }
        } catch (Exception e) {
            wifiEnabled=false;
            e.printStackTrace();
            Log.e(TAG,"热点创建失败");
        }
    }
    public void closeWifiHotspot() {
        try {
            if (!wifiEnabled)
                return;
            Method method = wifiManager.getClass().getMethod("getWifiApConfiguration");
            method.setAccessible(true);
            WifiConfiguration config = (WifiConfiguration) method.invoke(wifiManager);
            Method method2 = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method2.invoke(wifiManager, config, false);
            wifiEnabled=false;
            Log.e(TAG,"热点已关闭");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

