package com.upu.zenka.broadcasttest.tools;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ThinkPad on 2018/11/18.
 */

public  class geneDeviceCode {
    private static String deviceType="SPK0";
    public static String deviceCode(){
        return getMacid();
    }
    private static String getMac() {
        String macAddress = null;
        StringBuffer buf = new StringBuffer();
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("eth0");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {
                return "02:00:00:00:00:02";
            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (SocketException e) {
            e.printStackTrace();
            return "02:00:00:00:00:02";
        }
        return macAddress.replace(":","");
        //return macSerial;
    }
    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F' };
    private static String toHexString(byte[] b) {
        //String to byte
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }
    private static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            return toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    private static  String get16(){
        return  md5(getMac().replace(":","")).substring(5,21);
    }
    private static  String getMacid(){
        String temp=replaceIndex(2,get16(),"0");
        temp=replaceIndex(8,temp,"6");
        temp=replaceIndex(13,temp,"8");
        return deviceType+temp;
    }
    private static String replaceIndex(int index,String res,String str){
        return res.substring(0, index)+str+res.substring(index+1);
    }
    public static String geneRegisterCode(String macid){
        String temp=wipeNum(macid);
        temp=md5(temp);
        temp=getNum(temp);
        int len=temp.length();
        if (len>8){
            temp=temp.substring(0,8);
        }
        for (int i=len;i<8;i++){
            temp+="0";
        }
        return temp;
    }
    private static String wipeNum(String str){
        String temp="";
        for (int i=0;i<str.length();i++){
            char a = str.charAt(i);
            if (a >= '0' && a <= '9'){
                ;
            }
            else {
                temp+=a;
            }
        }
        return temp;
    }
    private static String getNum(String str){
        String temp="";
        for (int i=0;i<str.length();i++){
            char a = str.charAt(i);
            if (a >= '0' && a <= '9'){
                temp+=a;;
            }
        }
        return temp;
    }
}
