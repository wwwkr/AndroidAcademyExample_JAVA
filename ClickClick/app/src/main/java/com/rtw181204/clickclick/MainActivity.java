package com.rtw181204.clickclick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView iv;

    ImageView[] ivs = new ImageView[12];
    LinearLayout ll01;


    TextView tv;
    ImageView sIv;
    int num[]= new int[12];
    int a=0;
    int b=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random rnd = new Random();

        tv=findViewById(R.id.tv);
        sIv=findViewById(R.id.sIv);


        ll01=findViewById(R.id.ll01);

        for(int i=0; i<num.length;i++){
            num[i]=rnd.nextInt(12);
            for(int k=0;k<i;k++){
                if(num[i]==num[k]){
                    i--;
                }
            }
        }





    }

    public void clickTopBtn(View v){



            tv.setText("숫자를 순서대로 입력하세요");
            sIv.setImageResource(R.drawable.ing);

            for(int i = 0; i < ivs.length; i++){

                ivs[i]=findViewById(R.id.iv01+(i));

                ivs[i].setImageResource(R.drawable.num01+num[i]);

                for(int k = 0; k<ivs.length;k++){
                    if(i==num[k]){
                        ivs[i].setTag(""+num[i]);
                    }

                }




        }
    }

    public void clickBtn(View v){

        for(int i=0; i<ivs.length;i++){
            if(v.getTag().equals(a+"")){
                v.setVisibility(View.INVISIBLE);
                a++;
            }

            if(a==12){

                CtuGame(b);
                b++;
            }

        }


    }
    void CtuGame(int b){


        if(b==0) {
            for (int k = 0; k < ivs.length; k++) {
                a = 0;


                tv.setText("영어를 순서대로 눌러주세요");
                ivs[k].setVisibility(View.VISIBLE);
                ivs[k].setImageResource(R.drawable.alpa01 + num[k]);
                ll01.setBackgroundResource(R.drawable.bg2);

            }
        }


        else if(b==1){
            for (int k = 0; k < ivs.length; k++) {
                a = 0;

                tv.setText("그림을 순서대로 눌러주세요");
                ivs[k].setVisibility(View.VISIBLE);
                ivs[k].setImageResource(R.drawable.cha01 + num[k]);
                ll01.setBackgroundResource(R.drawable.bg3);

            }
        }

        else if(b==2){

            tv.setVisibility(View.INVISIBLE);
            sIv.setVisibility(View.INVISIBLE);
            ll01.setBackgroundResource(R.drawable.bg4);
        }

    }


}
