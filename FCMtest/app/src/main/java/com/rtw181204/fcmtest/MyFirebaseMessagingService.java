package com.rtw181204.fcmtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Push메세지를 받았을 때 자동으로 실행되는 메소드

        Log.i("TAG","onMessageReceived!!!!");

        //전달받은 데이터 알림(Notification)으로 공지하기

        Map<String, String> datas =  remoteMessage.getData();
        if(datas!=null && datas.size()>0){

            String name = datas.get("name");
            String msg = datas.get("msg");

            Log.i("TAG", "name : "+name + "msg : "+msg);

            //받은 메세지를 알림에 공지하기
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder builder = null;

            //오레오버전보다 낮으면 채널이 필요없음
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                String channelID = "ch01";
                String channelName = "channel 01";

                NotificationChannel notificationChannel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(notificationChannel);

                builder = new NotificationCompat.Builder(this, channelID);

            }else{
                builder = new NotificationCompat.Builder(this, null);
            }

            builder.setSmallIcon(R.drawable.icon_noti);
            builder.setContentTitle("FCM : "+name);
            builder.setContentText(msg);
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));

            //알림을 클릭했을 때 세부화면 액티비티로 전환
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("msg", msg);

            //보류중인 인텐트
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);

            //공지하기
            notificationManager.notify(1, builder.build());
        }
    }
}
