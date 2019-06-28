package com.upu.zenka.broadcasttest.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ThinkPad on 2018/11/19.
 */

public class SharedUtils {
    static SharedPreferences preferences;
    static String TAG="broadcastTest";

    //save data
    public static void saveBooleanData(Context context, String key, boolean value){
        if (preferences==null){
            preferences =context.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(key,value).commit();
    }
    //get save
    public static boolean getBooleanData(Context context,String key,boolean defValue){
        if (preferences==null){
            preferences =context.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        }

        return preferences.getBoolean(key,defValue);
    }
    public static void saveStringData(Context context,String key,String value){
        if (preferences==null){
            preferences =context.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(key,value).commit();
    }
    public static String getStringData(Context context,String key,String defValue){
        if (preferences==null){
            preferences =context.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        }

        return preferences.getString(key,defValue);
    }
    public static void saveIntData(Context context,String key,int value){
        if (preferences==null){
            preferences =context.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt(key,value).commit();
    }
    public static int getIntData(Context context,String key,int defValue){
        if (preferences==null){
            preferences =context.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        }
        return preferences.getInt(key,defValue);
    }
}
