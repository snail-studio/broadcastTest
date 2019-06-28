package com.upu.zenka.broadcasttest.tools;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by ThinkPad on 2018/12/28.
 */

public class Getip {
    //获取IP地址
    public static String getLocalIpStr(Context context){
        WifiManager wifiManager=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo=wifiManager.getConnectionInfo();
        return  intToIpAddr(wifiInfo.getIpAddress());
    }

    private static String intToIpAddr(int ip){
        return (ip & 0xFF)+"."
                + ((ip>>8)&0xFF) + "."
                + ((ip>>16)&0xFF) + "."
                + ((ip>>24)&0xFF);
    }
    public static String getHostIP() {

        String hostIp = "127.0.0.0";
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("yao", "SocketException");
            e.printStackTrace();
        }
        return hostIp;
    }
}
