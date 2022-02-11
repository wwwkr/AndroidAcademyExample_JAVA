package com.rtw181204.test09;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {


    TextView tv;
    MyFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);

        FragmentManager manager = getSupportFragmentManager();
        fragment = (MyFragment)manager.findFragmentById(R.id.frag);

        }


    public void clickBtn(View view) {

        fragment.tv.setText("AAAAAAAAAAAAAAAAA");
    }
}
