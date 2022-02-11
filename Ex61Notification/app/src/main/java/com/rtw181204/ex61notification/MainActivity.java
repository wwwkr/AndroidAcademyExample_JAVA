package com.rtw181204.ex61notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBtn(View view) {


        //알림(Notification)을 관리하는 관리자 객체 소환
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Notification객체 생성하기 (AlertDialog처럼 Bulider이용)
        NotificationCompat.Builder builder = null;

        //Oreo(API 26)버전 이상에서는 알림시에 NotificationChannel이라는 개념이 생김
        //NotificationChannel : 알림마다 채널을 설정하여 개별적인 설정 가능하도록

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_1";  //알림채널의 식별자
            String channelName = "CHANNEL 1";//알림채널의 이름

            //알림채널객체 생성(3번째 파라미터에 따라 알림의 표시형태가 바뀌므로 중요)
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            //IMPORTANCE_HIGH : 소리도 나고 알림이 펼쳐진 모습으로 화면 위쪽에 잠시 보여서 화면을 방해함
            //IMPORTANCE_DEFAULT : 소리도 나고 상태표시줄에 작은 아이콘만 표시됨(일반적인 모습)
            //IMPORTANCE_LOW : 소리가 안나고 상태표시줄에 작은 아이콘만 표시됨(아이콘표시 순서도 무조건 마지막)

            //알림매니저에게 알림채널 생성을 요청
            notificationManager.createNotificationChannel(channel);

            builder = new NotificationCompat.Builder(this, channelId);
        } else {
            builder = new NotificationCompat.Builder(this, null);
        }

        //Builder에게 알림객체의 설정들 적용하기

        //상태표시줄에 보여질 아이콘 설정
        builder.setSmallIcon(R.drawable.ic_sms);
        //상태표시줄에 잠시 보였다가 사라지는 글씨(19버전이상부터 안나옴)
        builder.setTicker("문자!");

        //상태바를 드래그하여 아래로 내리면 보이는
        //알림창(확장 상태바)의 설정
        builder.setContentTitle("알림제목");
        builder.setContentText("알림이 발생되었습니다.");
        builder.setSubText("추가적인 내용들은 여기에");


        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.img05);
        builder.setLargeIcon(bm);

        //그 밖의 설정들
        //진동추가( 퍼미션 필요)
        builder.setVibrate(new long[]{0,2000,1000,3000}); //0초대기, 2초진동, 1초대기 ,3초진동

        //소리추가
        Uri soundUri = RingtoneManager.getActualDefaultRingtoneUri(this,RingtoneManager.TYPE_RINGTONE);

        //res폴더>>raw폴더의 Uri ???
        soundUri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.get_gem);
        builder.setSound(soundUri);

        //알림창을 클릭시에 실행할 작업(SecondActivity)
        Intent intent = new Intent(this,SecondActivity.class);

        //지금 당장 시작하는 것이 아니라 잠시 실행을 보류해둔 Intent

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //알림창을 클릭했을 때 알림아이콘을 자동 제거
        //PendingIntent설정이 안되어 있으면 자동제거 안됨
        builder.setAutoCancel(true );

        //잘 사용하지 않는 기타 설정들(요즘 좀 사용)
        builder.addAction(android.R.drawable.ic_menu_manage,"setting",pendingIntent);

        builder.setColor(Color.MAGENTA);
        builder.setWhen(Calendar.getInstance().getTimeInMillis()-1000*60);

        //요즘들어 자주 보이는 알림창 스타일
        //1. BigPictureStyle
        NotificationCompat.BigPictureStyle picStyle = new NotificationCompat.BigPictureStyle(builder);
        picStyle.bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.img05));

        //2. BigTextStyle : 알림창에 많은 글씨가 보여지게 하려면
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle(builder);
        bigTextStyle.bigText("Hello. \n World \n Nice to meet you."); //줄바꿈이 안되는 디바이스도 있음

        //3. InboxStyle : 한줄씩 글씨 설정 가능

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle(builder);
        inboxStyle.addLine("aaa");
        inboxStyle.addLine("aaa");
        inboxStyle.addLine("aaa");
        inboxStyle.addLine("aaa");
        inboxStyle.addLine("aaa");

        //상태표시줄 표시
        builder.setProgress(100,50, true);







        //알림객체를 생성
        Notification notification = builder.build();

        //알림매니저에게 알림 요청
        notificationManager.notify(1,notification);

    }
}

