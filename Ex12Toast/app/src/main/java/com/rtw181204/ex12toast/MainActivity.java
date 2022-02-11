package com.rtw181204.ex12toast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Toast t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBtn(View view) {

/*
        if(t!=null){
            t.cancel();
        }
        //Toast객체 생성
        t = Toast.makeText(this,"토스트", Toast.LENGTH_SHORT);
        t.show();
        Toast.makeText(this,R.string.toast_msg,Toast.LENGTH_SHORT).show();*/

//        Toast t = Toast.makeText(this,"aaa",Toast.LENGTH_SHORT);
//
//        t.setGravity(Gravity.CENTER,Gravity.AXIS_X_SHIFT,Gravity.AXIS_Y_SHIFT);
//        t.show();

        //토스트에 보여지는 뷰가 기본은 TextView만 있음
        //토스트가 Custom View를 보여줄 수 있음

        //원하는 모양으로 Toast의 View를 만들기
        Toast t = Toast.makeText(this, "", Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER, 0, 0);

      /*  ImageView iv = new ImageView(this);

        iv.setImageResource(R.drawable.ic_launcher_background);

        TextView tv = new TextView(this);

        tv.setText("음소거모드");

        //여러개의 뷰를 넣으려면 뷰그룹으로 감싸서 넣기

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(iv);
        layout.addView(tv);

        t.setView(layout);
        t.show();*/

        //원하는 View를 자바로 만들면 코드가 너무 지저분함
        //그래서 View 생성을 xml언어로 사용할 수 있음

        //xml로 View 객체의 모양을 설계하고
        //이를 자바의 객체로 생성(부풀리다 inflate)시켜서 사용하고자함

        //res폴더 안에 layout폴더 안에 있는 toast.xml문서를
        //읽어서 자바 객체로 생성(부풀리는 inflate)하는 능력을 가진
        //객체를 운영체재 대리인 (Context)로 부터 얻어오기

        LayoutInflater inflater=getLayoutInflater();
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.toast,null);
        t.setView(layout);
        t.show();
    }
}

