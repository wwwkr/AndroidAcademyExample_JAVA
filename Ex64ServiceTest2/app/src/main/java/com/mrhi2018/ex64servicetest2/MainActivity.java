package com.mrhi2018.ex64servicetest2;

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

    public void clickPlay(View view) {
        //서비스 시작
        Intent intent= new Intent(this, MyService.class);
        startService(intent);
    }

    public void clickStop(View view) {
        //서비스 종료
        Intent intent= new Intent(this, MyService.class);
        stopService(intent);
    }
}
