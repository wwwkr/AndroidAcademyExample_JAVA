package com.rtw181204.ex25activity2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.et);
    }

    public void clickBtn(View view) {

        //EditText에 써있는

        String s = et.getText().toString();

        //SecondActivity에게 전달할 데이터를 택배기사(Intent)
        //d에게 추가하여 전달되도록 하기

        Intent intent = new Intent(this,SecondActivity.class);

        intent.putExtra("Name",s);
        intent.putExtra("Age",20);

        startActivity(intent);
    }
}
