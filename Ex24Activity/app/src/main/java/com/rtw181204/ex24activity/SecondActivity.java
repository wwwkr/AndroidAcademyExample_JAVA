package com.rtw181204.ex24activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SecondActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //화면에 보여줄 뷰 설정
        setContentView(R.layout.activity_second);
    }

    public void clickBtn(View v){

        Intent intent = new Intent(this,ThirdActivity.class);

        startActivity(intent);
    }
}
