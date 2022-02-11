package com.mrhi2018.ex37datastorageinternal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    EditText et;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et= findViewById(R.id.et);
        tv= findViewById(R.id.tv);

    }

    public void clickSave(View view) {

        //EditText에 있는 글씨를 얻어와서 내부저장소에 파일로 저장하기
        String data= et.getText().toString();
        et.setText("");

        //내부저장소에 Data.txt라는 이름의 파일에 문자열 데이터 저장
        //액티비티 클래스는 이미 내부저장소(Internal Storage)에
        //File로 저장할 수 있도록 Stream를 생성할 수 있는 기능(메소드)가 있음
        //즉, 안드로이는 앱에서 사용하는 내부저장소의 경로가 이미 고정되어 있음.
        // (phone마다 약간 다름..  "data/data/(앱의패키지명)/files"라는 곳에 저장

        try {
            FileOutputStream fos= openFileOutput("Data.txt", MODE_APPEND);
            PrintWriter writer= new PrintWriter(fos);//보조 문자열 스트림

            writer.println(data); //마치 System.out.println()처럼..사용
            writer.flush();
            writer.close();

            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }

    }

    public void clickLoad(View view) {

        try {
            FileInputStream fis= openFileInput("Data.txt");
            InputStreamReader isr= new InputStreamReader(fis);//문자스트림으로
            BufferedReader reader= new BufferedReader(isr);

            StringBuffer buffer= new StringBuffer();

            String line= reader.readLine();
            while (line!=null){
                buffer.append(line+"\n");
                line= reader.readLine();
            }

            tv.setText(buffer.toString());

            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
