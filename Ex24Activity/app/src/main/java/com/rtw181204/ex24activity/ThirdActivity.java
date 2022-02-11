package com.rtw181204.ex24activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);


        //제목줄(Titlebar --> Actionbar)의 제목변경
        //제목줄 객체 얻어오기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Third");
        //제목글씨 옆에 <-모양의 아이콘버튼 설정
        actionBar.setDisplayHomeAsUpEnabled(true);
        //업버튼이 되려면 눌렀을 때 돌아갈 Activity를 지정해줘야함
        //이 작업은 androidManifest.xml에서 작성함

    }
}
