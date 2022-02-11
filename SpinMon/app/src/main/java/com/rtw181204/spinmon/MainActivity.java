package com.rtw181204.spinmon;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp = MediaPlayer.create(this,R.raw.dragon_flight);
        mp.setLooping(true);


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(G.isMusic) mp.setVolume(0.5f, 0.5f);
        else mp.setVolume(0,0);

        mp.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mp!=null){
            mp.pause();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mp!=null){
            mp.stop();
            mp.release();
            mp = null;
        }
    }

    public void clickStart(View view) {

        //GameActivity로 전환
        Intent intent = new Intent(this,GameActivity.class);

        startActivity(intent);



    }

    public void clickExit(View view) {
        finish();
    }
}
