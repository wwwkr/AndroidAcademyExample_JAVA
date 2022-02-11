package com.rtw181204.ex63broadcastreceiverbooting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootingReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if(action.equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent intent1 = new Intent(context, MainActivity.class);
            context.startActivity(intent1);
        }
    }
}
