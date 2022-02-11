package com.rtw181204.ex27activity4;

import android.content.Intent;
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

        //SecondActivity실행

        Intent intent = new Intent();
       // intent.setAction("aaa");
        intent.setAction("bbb");
        //묵시적 인텐트는 위의 action값("aaa")을
        //intent-filter로 가지고 있는 액티비티를 찾기 위해
        //이 앱이 설치된 phone의 모든 앱들을 검색함.

        startActivity(intent);
    }
}
