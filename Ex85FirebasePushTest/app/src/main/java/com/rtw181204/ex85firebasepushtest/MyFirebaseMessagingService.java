package com.rtw181204.ex85firebasepushtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //메세지를 받으면 실행되는 메소드
        //메세지 내용은 파라미터로 전달된 remoteMessage로 부터 얻어오면 됨

        Log.e("TAG","received message");

        Map<String, String> datas = remoteMessage.getData();


        if(datas!=null){
            //전달된 메세지 가져오기
            String name = datas.get("name");
            String msg = datas.get("msg");

            //받은 메세지를 알림(Notification)으로 보여주기
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder builder = null;
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

                String channelId = "ch01";
                String channelName = "Channel 01";

                NotificationChannel nofiChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(nofiChannel);

                builder = new NotificationCompat.Builder(this, channelId);

            }else{

                builder = new NotificationCompat.Builder(this, null);

            }

            builder.setSmallIcon(android.R.drawable.ic_dialog_email);
            builder.setContentTitle(name);
            builder.setContentText(msg);
            builder.setAutoCancel(true);

            Notification notification = builder.build();
            notificationManager.notify(100,notification);
        }




    }
}
