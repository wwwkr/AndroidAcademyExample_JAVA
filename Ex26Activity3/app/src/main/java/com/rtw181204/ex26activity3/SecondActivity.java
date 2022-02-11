package com.rtw181204.ex26activity3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {

    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        et=findViewById(R.id.et);


    }

    public void clickOk(View view) {

        //EditText에 써있는 글씨를 얻어와서
        String s = et.getText().toString();

        //이 액티비티에 온 택배기사(Intent객체)에게
        //데이터를 Extra로 추가하여 결과를 되돌려주기
        Intent intent = getIntent();
        intent.putExtra("Name",s);
        intent.putExtra("Age",30);

        //여기까지가 결과완성이다
        setResult(RESULT_OK,intent);

        //더 이상 이 액티피비가 있을 이유가 없으니 종료
        finish();




    }
}
