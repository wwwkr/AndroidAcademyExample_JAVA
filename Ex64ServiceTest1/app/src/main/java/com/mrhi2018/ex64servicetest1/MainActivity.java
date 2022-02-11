package com.mrhi2018.ex64servicetest1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;//별도 Thread

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickPlay(View view) {
        if(mp==null){
            mp= MediaPlayer.create(this, R.raw.kalimba);
            mp.setLooping(false);

            mp.setVolume(0.7f, 0.7f);
            mp.start();
        }
    }

    public void clickStop(View view) {
        if(mp!=null && mp.isPlaying()){
            mp.stop();
            mp.release();
            mp=null;
        }
    }

    //뒤로가기버튼 눌렀을 때 액티비티가 종료되지 않도록 하고싶다면!!
    @Override
    public void onBackPressed() {
        //super.onBackPressed(); //<--이것때문에 finish가 됨.
        //액티비티를 백스택으로 이동시키는 방법
        //1. 액티비티를 백스택으로 이동시키기
        //moveTaskToBack(true);

        //2. 홈스크린 액티니티를 실행(자연스럽게 이 액티비티는 백스택으로 이동)
        Intent intent= new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);

    }
}
