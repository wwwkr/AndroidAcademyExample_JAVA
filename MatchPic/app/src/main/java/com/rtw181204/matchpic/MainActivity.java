package com.rtw181204.matchpic;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView[] ivs = new ImageView[5];
    TextView tv;
    int[] num = new int[5];

    Random rnd = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        tv=findViewById(R.id.tv);

        for(int i =0; i<ivs.length;i++){
            ivs[i]=findViewById(R.id.iv01+i);
        }




    }

    public void clickBtn(View view) {


        for(int i = 0 ; i < num.length;i++){
            num[i]=rnd.nextInt(5);
            for(int j = 0; j<i;j++){
                if(num[i]==num[j]){
                    i--;
                }
            }
        }

        for(int i =0; i<ivs.length;i++){

            ivs[i].setImageResource(R.drawable.a_ele+num[i]);

        }

        tv.setBackgroundResource(R.drawable.b_ele+rnd.nextInt(5));





    }

    public void clickBtn2(View view) {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);


        builder.setTitle("설명서");
        builder.setMessage("펫말에 나오는 이름과 일치하는 동물을 클릭하면 됩니다.");
        builder.setPositiveButton("확인",null);



        builder.create().show();

    }
}
