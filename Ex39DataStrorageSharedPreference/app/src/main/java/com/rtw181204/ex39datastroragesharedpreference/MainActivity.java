package com.rtw181204.ex39datastroragesharedpreference;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    EditText et_name, et_age;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        et_name= findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        tv = findViewById(R.id.tv);


    }

    public void clickSave(View view) {

        String name = et_name.getText().toString();
        int age;

        try {
            age = Integer.parseInt(et_age.getText().toString());
        }
        catch (Exception e){
            age=0;
        }

        //내부저장소에 Preference로 저장하기
        //Data.xml
        SharedPreferences pref = getSharedPreferences("Data",MODE_PRIVATE);

        //저장(쓰기)작업시작을 요청하면 자동으로 작성자객체(Edior)를 리턴해줌
        SharedPreferences.Editor editor =pref.edit();

        //저장작업 시작(키-벨류 쌍으로)
        editor.putString("Name",name);
        editor.putInt("Age",age);

        //쓰기작업이 완료되었다고 명시
        editor.commit(); //이거 안하면 저장 안됌
    }

    public void clickLoad(View view) {


        //SharedPreferences 객체 소환
        SharedPreferences pref = getSharedPreferences("Data",MODE_PRIVATE);

        //읽기는 곧바로 preference객체에게 요청
        String name = pref.getString("Name","no name");
        int age = pref.getInt("Age",0); //두번째 파라미터 : 저장된게 없을때의 값

        tv.setText(name + " , "+age);


    }
}
