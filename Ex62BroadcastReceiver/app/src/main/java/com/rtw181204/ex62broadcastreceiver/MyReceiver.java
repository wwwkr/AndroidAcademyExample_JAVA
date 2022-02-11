package com.rtw181204.ex62broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //리시버가 방송을 수신하였을 때 자동으로 실행되는 메소드

        String action = intent.getAction();

        if(action.equals("aaa")){
            Toast.makeText(context, "aaa받았다", Toast.LENGTH_SHORT).show();

        }else if(action.equals("bbb")){
            Toast.makeText(context, "bbb받았다", Toast.LENGTH_SHORT).show();

        }else if(action.equals("android.provider.Telephony.SMS_RECEIVED")){
            Toast.makeText(context, "SMS받았다", Toast.LENGTH_SHORT).show();
        }

    }
}
