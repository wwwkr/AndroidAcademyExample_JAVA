package com.rtw181204.numgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    //참조변수
    EditText et;
    Button btn;
    TextView tv;

    //정답값을 저장하는 변수
    int com;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //정답값생성
        Random rnd = new Random();

        com = rnd.nextInt(501)+500; //500-1000

        et.findViewById(R.id.edit);
        btn.findViewById(R.id.btn);
        tv.findViewById(R.id.tv);

        //버튼이 클릭되면 EditText에 입력된 숫자를 얻어와서
        //정답값과 비교!

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //EditText에 써있는 글씨(숫자) 얻어오기
                Editable editable = et.getEditableText();
                String str = editable.toString();
                int user = Integer.parseInt(str);

                //얻어온 글씨와 정답값(com)비교
                if(user>com){
                    tv.setText(user+"보다 작습니다.");
                }
                else if(user<com){
                    tv.setText(user+"보다 큽니다.");
                }
                else{
                    tv.setText("축하합니다.\n정답입니다.");
                }

            }
        });

    }
}
