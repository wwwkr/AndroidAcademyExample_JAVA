package com.rtw181204.ex25activity2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {


    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        tv= findViewById(R.id.tv);
        //이 액티비티를 실행시켜준 택배기사 참조하기
        Intent intent = getIntent();
        //택배기사(Intent)에게 Extra데이터가 있는지..
        //있으면 값 얻어오기
        String name = intent.getStringExtra("Name"); //만약 없으면 null을 리턴
        int age = intent.getIntExtra("Age",0);
        tv.setText(name +" "+ age);



    }
}
