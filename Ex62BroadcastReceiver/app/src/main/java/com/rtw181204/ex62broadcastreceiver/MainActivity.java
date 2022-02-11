package com.rtw181204.ex62broadcastreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBtn(View view) {

        //명시적 인텐트로 리시버 실행(본인 앱 안에있는 리시버만 가능)
//        Intent intent = new Intent(this,MyReceiver.class);
//        sendBroadcast(intent);

        //방송 송신(특정문자열)
        //"android.intent.action.BOOT_COMPLETED"
        //암시적 인텐트(Oreo버전부터는 불가능)
        Intent intent = new Intent();
        intent.setAction("aaa");

        //Phone에 설치되어 있는 모든 앱들에게 방송 송신
        sendBroadcast(intent);

    }

    public void clickBtn2(View view) {

        Intent intent = new Intent("kkk");
        sendBroadcast(intent);
    }

    //TestReceiver가 멤버변수로
    TestReceiver receiver;

    //앱이 화면에 보여질때
   @Override
    protected void onResume() {
        super.onResume();

        //TestReceiver가 "kkk"라는 방송을 수신하도록 등록
       receiver = new TestReceiver();
       IntentFilter intentFilter = new IntentFilter();
       intentFilter.addAction("kkk");

       //필터와 리시버를 등록
       registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();


        unregisterReceiver(receiver);
    }
}
