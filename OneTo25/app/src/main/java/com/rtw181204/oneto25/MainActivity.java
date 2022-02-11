package com.rtw181204.oneto25;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //xml에서 만든 View객체들의 참조변수
    TextView tv; //현재눌러야될 버튼의 번호
    Button btnretry;//재시작버튼

    Button[] btns = new Button[25];

    int num=1; //현재 눌러야될 번호

    Drawable btnBack; // 버튼의 기본 배경 이미지 참조변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        tv=findViewById(R.id.tv);
        btnretry=findViewById(R.id.btn_retry);

        //버튼의 기본 배경이미지를 얻어오기
        btnBack= btnretry.getBackground();

        Random rnd = new Random();

        int[] nums = new int[25];

        for(int i=0;i<nums.length;i++){
            nums[i]=rnd.nextInt(25)+1;

           for(int j=0;j<i;j++){
                if(nums[i]==nums[j]){
                    i--;
                    break;
                }
            }
        }


        for(int i=0; i<btns.length;i++){
            btns[i]=findViewById(R.id.btn01+i);

            btns[i].setText(nums[i]+"");



        }







    }//onCreate

    //onClick속성이 부여된 View가 클릭되면 자동으로 실행되는 콜백메소드
    //반드시 접근제한자가 있어야되고 리턴타입이 void여야함
    public void clickBtn(View v){

       Button btn = (Button)v;

        //클릭된 View(버튼 v)에 써있는 Text를 얻어와서
        //현재 눌러야될 번호(num)의 값과 같은지 비교

        String s = btn.getText().toString();
        int n=Integer.parseInt(s);
        if(num==n){
            //같으면 현재번째가 맞으므로 버튼글씨를 OK로
            btn.setText("OK");
            btn.setTextColor(0xFFFF00FF);
            btn.setBackgroundColor(Color.TRANSPARENT);
            //btn.setVisibility(View.INVISIBLE);
            num++;
            tv.setText(num+"");

        }
        if(num>25){
            tv.setText("CLEAR!!");
            //리트라이버튼 활성화
            btnretry.setEnabled(true);
        }

    }

    public void clickRetry(View v){
        //모든 버튼들에 다시 번호를 부여



        Random rnd = new Random();

        int[] nums = new int[25];

        for(int i=0;i<nums.length;i++){
            nums[i]=rnd.nextInt(25)+1;

            for(int j=0;j<i;j++){
                if(nums[i]==nums[j]){
                    i--;
                    break;
                }
            }
        }

        for(int i = 0 ;i <btns.length;i++){
            btns[i].setText(nums[i]+"");
            btns[i].setTextColor(Color.BLACK);
            btns[i].setBackgroundDrawable(btnBack);

        }


        num=1;
        tv.setText(num+"");
        btnretry.setEnabled(false);
    }
}//MainActivity
