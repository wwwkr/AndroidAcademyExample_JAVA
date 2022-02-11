package com.rtw181204.hello;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Java로 View 객체를 만들어보기
        tv= new TextView(this);
        tv.setText("Hello world!!!");
        tv.setTextSize(50);
        tv.setTextColor(Color.BLUE);

        Button btn = new Button(this);
        btn.setText("확인");
        //Activity는 한번에 하나의 View만 보여줄 수 있음
        //그러므로 여러개를 보여주려면 ViewGroup을 사용해야함

        btn.setOnClickListener(new View.OnClickListener() {

            //버튼이 클릭되었을때 자동으로 호출되는 메소드 : 콜백메소드
            @Override
            public void onClick(View v) {
                tv.setText("Nice");
            }
        });
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(tv);
        layout.addView(btn);
        setContentView(layout);


    }
}
