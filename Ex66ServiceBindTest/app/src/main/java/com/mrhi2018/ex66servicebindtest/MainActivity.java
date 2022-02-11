package com.mrhi2018.ex66servicebindtest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MusicService musicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //액티비티가 화면에 보여질 때 자동으로 실행되는 콜백메소드
    @Override
    protected void onResume() {
        super.onResume();

        //MusicService와 연결(bind)작업 수행!!
        if(musicService==null){
            //MusicService를 실행시킬 Intent생성
            Intent intent= new Intent(this, MusicService.class);
            startService(intent);//서비스객체가 없으면 create를 하고 있다면 onStartCommand()메소드만 다시 호출

            //세번째 파리미터
            //0: 플래그를 안주는 값. 이 값을 주면 자동으로 서비스 객체를 생성하지 않고..생성되어 있는 서비스 객체와 연결만 함.
            //BIND_AUTO_CREATE : 만약 연결할 서비스객체 자동 생성, 기존 서비스와 다른 객체가 또 만들어 질 수도 있음.
            bindService(intent, conn, 0);
        }


    }

    //멤버변수 위치
    //MusicService와 연결된 연결통로를 관리하는 객체
    ServiceConnection conn= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            //bindService()로 서비스객체와 연결을 시도하여 성공하였을 때 자동 실행되는 메소드
            //두번째 파라미터 : 서비스객체의 onBind()메소드에서 리턴된 객체
            MusicService.MyBinder myBinder= (MusicService.MyBinder)binder;
            musicService= myBinder.getServieAddress();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    public void clickPlay(View view) {
        if(musicService!=null) musicService.playMusic();
    }

    public void clickPause(View view) {
        if(musicService!=null) musicService.pauseMusic();
    }

    public void clickStop(View view) {
        if(musicService!=null){
            musicService.stopMusic();
            //서비스객체와 연결(bind) 끊기!!
            unbindService(conn);
            musicService=null;
        }

        Intent intent= new Intent(this, MusicService.class);
        stopService(intent);

        finish();
    }
}
