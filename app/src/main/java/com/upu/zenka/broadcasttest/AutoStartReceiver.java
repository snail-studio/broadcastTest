package com.upu.zenka.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ThinkPad on 2018/11/26.
 */

public class AutoStartReceiver extends BroadcastReceiver {
    static final String action_boot ="android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals(action_boot)){
            Intent i =new Intent(context,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}