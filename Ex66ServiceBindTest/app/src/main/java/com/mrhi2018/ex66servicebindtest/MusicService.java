package com.mrhi2018.ex66servicebindtest;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {

    MediaPlayer mp;

    //MainActivity쪽에 MusicService객체의 참조주소를
    //전달해줄 직원객체 설계
    class MyBinder extends Binder{
        //MusicService객체의 참조주소를 리턴시켜주는 기능
        public MusicService getServieAddress(){
            return MusicService.this;
        }

    }

    //bindService()를 실행해서 연결되었을때 자동 호출되는 메소드
    @Override
    public IBinder onBind(Intent intent) {
        //Binder객체 생성
        MyBinder myBinder= new MyBinder();
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    //MainAcivity쪽에서 제어할 메소드들
    public void playMusic(){
        if(mp==null){
            try {
                mp= new MediaPlayer();
                Uri uri= Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.maid);
                mp.setDataSource(this, uri);
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //플레이 중이 아니면...
        if(!mp.isPlaying()) mp.start();
    }

    public void pauseMusic(){
        if(mp!=null) mp.pause();
    }

    public void stopMusic(){
        if(mp!=null){
            mp.stop();
            mp.release();
            mp=null;
        }
    }


}
