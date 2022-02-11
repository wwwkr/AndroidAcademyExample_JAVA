package com.rtw181204.test01;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText et01,et02,et03,et04;
    RadioGroup rg01,rg02;
    Button btn02;
    TextView tv05;
    RadioButton rb01;
    RadioButton rb02;
    CheckBox cb01,cb02,cb03,cb04;
    String s="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et01=findViewById(R.id.et01);
        et02=findViewById(R.id.et02);
        et03=findViewById(R.id.et03);
        et04=findViewById(R.id.et04);

        rg01=findViewById(R.id.rg01);
        rg02=findViewById(R.id.rg02);

        btn02=findViewById(R.id.btn02);
        tv05=findViewById(R.id.tv05);

        cb01=findViewById(R.id.cb01);
        cb02=findViewById(R.id.cb02);
        cb03=findViewById(R.id.cb03);
        cb04=findViewById(R.id.cb04);



        RadioGroup.OnCheckedChangeListener changeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                rb01= findViewById(checkedId);


            }
        };
        RadioGroup.OnCheckedChangeListener Listener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                rb02= findViewById(checkedId);


            }
        };



        rg01.setOnCheckedChangeListener(changeListener);
        rg02.setOnCheckedChangeListener(Listener);
    }

    public void clickBtn(View view) {



        String str="";

        if(cb01.isChecked()){
            str+= cb01.getText().toString()+ " ";
        }
        if(cb02.isChecked()){
            str+= cb02.getText().toString()+ " ";
        }
        if(cb03.isChecked()){
            str+= cb03.getText().toString()+ " ";
        }
        if(cb04.isChecked()){
            str+= cb04.getText().toString()+ " ";
        }



        tv05.setText(s+=et01.getText()+" "+rb01.getText().toString()+" "+rb02.getText().toString()+
                " "+et02.getText()+"-"+et03.getText()+"-"+et04.getText()+" "+str+"\n");
    }
}
