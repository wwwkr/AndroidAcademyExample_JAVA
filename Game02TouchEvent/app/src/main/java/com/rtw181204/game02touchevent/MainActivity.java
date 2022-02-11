package com.rtw181204.game02touchevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));


        //제목줄제거
        getSupportActionBar().hide();

        //상태표시줄제거(전체화면모드)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        Toast.makeText(this, "activity touch", Toast.LENGTH_SHORT).show();
        return super.onTouchEvent(event);
    }
}
