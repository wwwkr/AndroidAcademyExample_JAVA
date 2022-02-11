package com.rtw181204.ex76alarmmanagertest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //리시버가 방송을 수신하였을 때 자동 실행되는 메소드

        Toast.makeText(context, new Date().toString(), Toast.LENGTH_SHORT).show();

        //알람매니저 객체 소환
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        //반복알람시에 실행할 컴포넌트 PendingIntent(BroadcastReceiver)
        Intent intent2 = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,20,intent2,PendingIntent.FLAG_UPDATE_CURRENT);


        //반복알람을 위해 새로운 알람을 다시 예약
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+1000*2,pendingIntent);

        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+1000*2,pendingIntent);

        }
    }
}
