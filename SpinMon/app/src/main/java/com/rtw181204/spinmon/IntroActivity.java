package com.rtw181204.spinmon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class IntroActivity extends AppCompatActivity {


    ImageView iv;

    //스케쥴 관리객체
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        iv = findViewById(R.id.iv);
        //View에게 애니메이션 효과를 주는 객체 생성
        //자바로 Animation객체를 만들어도 되지만
        //여러개일때는 코드가 지저분함

//        Animation ani = new AlphaAnimation(0.0f, 1.0f);
//        ani.setDuration(3000);

        //그래서 애니메이션에 대한 설계를 xml로 하고
        //이를 자바에서 불러서 사용함
        //res>>anim>>appear_logo.xml을 읽어와서
        //Animation객체로 생성하기

       Animation ani =  AnimationUtils.loadAnimation(this,R.anim.appear_logo);
        iv.startAnimation(ani);

        //스케쥴 등록
        timer.schedule(task,4000 ); //4초 후에 task객체 start()


    }

    //멤버변수 위치
    //Thread를 상속받아 만들어진 TimerTask라는 클래스를 객체로 생성
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            //MainActivity를 실행
            Intent intent = new Intent(IntroActivity.this,MainActivity.class);

            startActivity(intent);

            finish(); //IntroAcitivity를 메모리에서 완전 제거

        }
    };

    //화면에 보여질때
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }


    void loadData(){

        SharedPreferences pref = getSharedPreferences("Data",MODE_PRIVATE);

        G.gem = pref.getInt("Gem",0);
        G.champion = pref.getInt("Champion",0);
        G.championImagePath = pref.getString("ChampionImagePath",null);

        G.isMusic = pref.getBoolean("Music",true);
        G.isSound = pref.getBoolean("Sound",true);
        G.isVibrate = pref.getBoolean("Vibrate",true);

    }
}
