package com.upu.zenka.broadcasttest.tools;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ThinkPad on 2018/11/22.
 */

public class getTime {
    private static long curtime=0;
    public static String time(){
        long sysTime = getCurtime();
        CharSequence sysTimeStr = DateFormat.format("yyyy-MM-dd HH:mm:ss", sysTime);
        return sysTimeStr.toString();
    }
    public static String gettime(){
        long sysTime = getCurtime();
        CharSequence sysTimeStr = DateFormat.format("HH:mm:ss", sysTime);
        return sysTimeStr.toString();
    }
    private static String getTimeStr(int index){
        String time="";
        long sysTime = getCurtime();
        CharSequence sysTimeStr = DateFormat.format("yyyy-MM-dd HH:mm:ss", sysTime);
        time=sysTimeStr.toString();
        time=time.substring(0,11)+index+":00:00";
//        switch (index){
//            case 10:
//                time=time.substring(0,11)+"10"+":00:00";
//                break;
//            case 15:
//                time=time.substring(0,11)+"15"+":00:00";
//                break;
//            case 19:
//                time=time.substring(0,11)+"19"+":00:00";
//                break;
//            default:
//                break;
//        }
        return time;
    }
    private static String getTimeStr(String str){
        String time="";
        long sysTime = getCurtime();
        CharSequence sysTimeStr = DateFormat.format("yyyy-MM-dd HH:mm:ss", sysTime);
        time=sysTimeStr.toString();
        time=time.substring(0,11)+str;
        return time;
    }
    public static long calTime(String strTime){
        try {
            SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(strTime);
            return date.getTime();
        }catch (Exception e) {
            return 0;
        }
    }

    public static long getLongtime(int hours){
        return  calTime(getTimeStr(hours));
    }
    public static long getLongtime(String time){
        return  calTime(getTimeStr(time));
    }
    public static boolean isAmSettime(long time){
        if (publicParam.amtimeIn<time&&time<publicParam.amtimeLeave){
            return  true;
        }else{
            return false;
        }
    }
    public static boolean isPmSettime(long time){
        if (publicParam.pmtimeIn<time&&time<publicParam.pmtimeLeave){
            return  true;
        }else{
            return false;
        }
    }
    public static long getCurtime(){
        curtime=System.currentTimeMillis()+publicParam.OFFSET;
        //curtime=System.currentTimeMillis();
        return curtime;
    }
}
