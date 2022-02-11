package com.rtw181204.ex41viewflipper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {


    ViewFlipper flipper;
    ToggleButton toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flipper = findViewById(R.id.flipper);

        //ViewFlipper안에 7개의 이미지뷰를 추가로 포함시키기

        for(int i =0; i<5; i++){
            ImageView iv= new ImageView(this);
            iv.setImageResource(R.drawable.gametitle_04+i);

            flipper.addView(iv);
        }

        toggle = findViewById(R.id.toggle);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    flipper.setFlipInterval(1000);
                    flipper.startFlipping();
                }
                else{
                    flipper.stopFlipping();
                }
            }
        });
    }

    public void clickPrev(View view) {


        //이전 페이지로 이동
        flipper.showPrevious();
    }

    public void clickNext(View view) {

        //다음 페이지로 이동
        flipper.showNext();
    }
}
