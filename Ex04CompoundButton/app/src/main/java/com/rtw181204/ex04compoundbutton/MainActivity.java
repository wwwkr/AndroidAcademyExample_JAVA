package com.rtw181204.ex04compoundbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {


    CheckBox cb01,cb02,cb03;
    TextView tv;
    ToggleButton toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       cb01=findViewById(R.id.cb01);
       cb02=findViewById(R.id.cb02);
       cb03=findViewById(R.id.cb03);

       tv=findViewById(R.id.tv);


        //CheckedChange리스너 객체생성
        CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String s="";

                //CheckBox들 중에 check된 객체를 찾아서 그 글씨를 얻어오기

                if(cb01.isChecked())s+=cb01.getText().toString();
                if(cb02.isChecked())s+=cb02.getText().toString();
                if(cb03.isChecked())s+=cb03.getText().toString();

                tv.setText(s);
            }
        };
        //CheckBox의 Check상태가 변경되는 것에 반응하기 위해
        //리스너 객체 생성 및 설정
        cb01.setOnCheckedChangeListener(changeListener);
        cb02.setOnCheckedChangeListener(changeListener);
        cb03.setOnCheckedChangeListener(changeListener);


        toggle=findViewById(R.id.toggle);

        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tv.setText(isChecked+"");
            }
        };
        toggle.setOnCheckedChangeListener(listener);
    }
    public void clickBtn(View v){

        String s="";

        //CheckBox들 중에 check된 객체를 찾아서 그 글씨를 얻어오기

        if(cb01.isChecked())s+=cb01.getText().toString();
        if(cb02.isChecked())s+=cb02.getText().toString();
        if(cb03.isChecked())s+=cb03.getText().toString();

        tv.setText(s);
    }
}
