package com.rtw181204.ex24activity;

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

        //SecondActivity 실행
        //직접 MainActivity가 실행할 수는 없고
        //대신 실행시켜주는 택배아저씨(Intent객체)를 생성하여 요청해야함

        Intent intent = new Intent(this,SecondActivity.class);

        startActivity(intent);

        //이 MainActivity를 완전 종료시킬 수 있음




    }

    public void clickBtn2(View view) {

        //ThirdAcitivity 실행

        Intent intent = new Intent(this,ThirdActivity.class);


        startActivity(intent);

    }
}
