package com.rtw181204.game01customview;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        MyView myView = new MyView(this);

        setContentView(myView);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //상태표시줄(StatusBar)제거 (전체화면모드)
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }
}
