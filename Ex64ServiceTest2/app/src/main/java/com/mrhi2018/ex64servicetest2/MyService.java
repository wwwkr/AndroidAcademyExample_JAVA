package com.mrhi2018.ex64servicetest2;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {

    MediaPlayer mp;

    //startService()로 서비스객체가 실행될 때 자동으로 호출되는 메소드
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(mp==null){
            mp= MediaPlayer.create(this, R.raw.sleep_away);
            mp.setLooping(true);
            mp.setVolume(0.7f, 0.7f);
        }

        mp.start();

        //Notification보이기!!

        //메모리문제로 프로세스가 강제로 이 서비스를 kill시킬수도 있음.
        //죽었다가 메모리 문제가 해소되면 자동으로 다시 시작되도록..
        //하는 설정값 "START_STICKY"
        return START_STICKY;
    }

    //stopService()가 호출될 때 서비스가 종료되면서 자동으로 실행되는 메소드
    @Override
    public void onDestroy() {

        if(mp!=null){
            mp.stop();
            mp.release();
            mp=null;
        }

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
