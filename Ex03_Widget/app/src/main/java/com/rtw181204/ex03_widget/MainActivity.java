package com.rtw181204.ex03_widget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {


    ImageView iv;
    Button btn1,btn2,btn3,btn4;
    int num=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv=findViewById(R.id.iv);

        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);


        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);

        //ImageView에 Clickable "true" 속성을 부여하면 클릭에 반응 가능
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이미지뷰의 이미지를 변경

                if(num==7){
                    num=0;
                }
                iv.setImageResource(R.drawable.australia+num);
                num++;
            }
        });
    }

    //클릭을 듣는 리스너객체 생성
    View.OnClickListener listener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            int id = v.getId();


            switch (id){
                case R.id.btn1:
                    iv.setImageResource(R.drawable.australia);
                    break;

                case R.id.btn2:
                    iv.setImageResource(R.drawable.belgium);
                    break;

                case R.id.btn3:
                    iv.setImageResource(R.drawable.canada);
                    break;

                case R.id.btn4:
                    iv.setImageResource(R.drawable.korea);
                    break;


            }
        }
    };


}
