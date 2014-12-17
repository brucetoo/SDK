package com.example.SDK;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Number    N1007
 * Creator   tuyong
 * On        2014/12/17
 * time      13:04
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    static final String action_boot="android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(action_boot) && (SharePreUtils.getFloat(context,"boot") == 1)){
            Intent ootStartIntent=new Intent(context,MainActivity.class);
            ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(ootStartIntent);
        }

    }

}
