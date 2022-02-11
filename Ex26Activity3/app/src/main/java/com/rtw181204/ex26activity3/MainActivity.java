package com.rtw181204.ex26activity3;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv= findViewById(R.id.tv);
    }

    public void clickBtn(View view) {

        Intent intent = new Intent(this,SecondActivity.class);
        //startActivity(intent); 이 메소드로 호출하면 결과를 받을 수 없음.
        startActivityForResult(intent,10);//이 메소드를 써야 intent객체가 돌아옴
    }


    //startActivityForResult()메소드로 실행시킨 액티비티가 종료되면
    //자동으로 Intent객체가 돌아옴
    //Intent가 돌아오면 자동으로 실행되는 메소드가 있음


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //3번째 파라미터(매개변수) : 돌아온 intent 객체

        switch (requestCode){

            case 10:

                if(resultCode==RESULT_OK)
                    tv.setText(data.getStringExtra("Name")+ " , "+data.getIntExtra("Age",0));
                break;
        }
    }
}
