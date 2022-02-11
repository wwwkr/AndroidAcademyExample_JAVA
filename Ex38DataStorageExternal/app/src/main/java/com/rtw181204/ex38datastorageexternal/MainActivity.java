package com.rtw181204.ex38datastorageexternal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    EditText et;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        et = findViewById(R.id.et);
        tv = findViewById(R.id.tv);


    }

    public void clickSave(View view) {

        //외부메모리(SD Card)가 있는지?
       String state= Environment.getExternalStorageState();

       //외부메모리 상태가 연결(Mounted)되어있지 않은지 확인

        if(!state.equals(Environment.MEDIA_MOUNTED)){

            Toast.makeText(this, "SDcard is not mounted", Toast.LENGTH_SHORT).show();
            return;
        }

        //위에서 return이 안되었다면 연결되어있음으로 저장작업시작
        String data = et.getText().toString();
        et.setText("");

        //Data.txt라는 이름으로 파일을 저장하고자 함
        //저장될 파일의 디렉토리 경로지정이 필요함
        //폴더위치를 직접 작성하지 않고 경로를 받아와야함

        File path; //디렉토리 경로객체 참조변수

        //api 19버전(kitket)이상의 폰과 그 이전의 폰이 코드가 다름

        File[] dirs;
        if(Build.VERSION.SDK_INT>=19) {
          dirs = getExternalFilesDirs("MyDir");//파일이 저장된 폴더명

        }else{
            //19버전 이상의 API기능을 사용하기 위해 호환성버전 클래스(ConterxtCompat)를 이용
          dirs =  ContextCompat.getExternalFilesDirs(this,"MyDir");
        }

        path = dirs[0];

        tv.setText(path.getAbsolutePath());

        //위에 설정된 경로안에 Data.txt라는 이름의 파일까지 포함시키기

        File file = new File(path,"Data.txt");

        try {
            FileWriter fw = new FileWriter(file,true); //두번째 파라미터 : 저장데이터 추가하기모드
            PrintWriter writer = new PrintWriter(fw);

            writer.println(data);
            writer.flush();
            writer.close();
            Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show();


        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "FAILED", Toast.LENGTH_SHORT).show();
        }

    }

    public void clickLoad(View view) {

        String state = Environment.getExternalStorageState();

        if(state.equals(Environment.MEDIA_MOUNTED) ||
                state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){


            //읽을 수 있는 상태
            File path;
            File[] dirs;

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){

                dirs = getExternalFilesDirs("MyDir");

            }else {

                dirs= ContextCompat.getExternalFilesDirs(this,"Mydir");
            }

            path = dirs[0];

            File file = new File(path,"Data.txt");

            try {
                FileReader fr = new FileReader(file);

                BufferedReader reader = new BufferedReader(fr);

                StringBuffer buffer = new StringBuffer();

                String line = reader.readLine();

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

    //동적퍼미션이 필요한 외부저장소 경로
    //(본인 앱 패키지명 폴더(Storage/../android//data/com.....) 말고 나머지 다)
    public void clickBtn(View view) {

        String state = Environment.getExternalStorageState();

        if(!state.equals(Environment.MEDIA_MOUNTED)){

            Toast.makeText(this, "외부저장소없음", Toast.LENGTH_SHORT).show();
            return;
        }


        //외부저장소를 사용하려면 사용자에게 허가(permisson)을 받아야함
        //Manifest.xml에 퍼미션 사용표시(25버전 미만에서는 이것만 하면 되었음)
        //위 방식은 처음 앱을 다운받을 때 한번만 퍼미션사용을 사용자에게 공지함
        //위 방식이 보안의 고나점에서 문제가 있따는 인식이 생기면서
        //사용자가 앱을 실행할때 마다 마다 물어보게끔 (물론, 한번 선택하면 안물어보게 할 수 있음)
        //이런 방식을 동적퍼미션이라고 함
        //이제는 이런 방식이 매우 보편화됌 ex) 카메라, 연락처, 위치정보 etc

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

            //동적퍼미션 작업 (다이얼로그로 보여줘서 설정할 수 있게)

            int checkSelfPermission =checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            //퍼미션이 허용되어 있지 않은지
            if(checkSelfPermission == PackageManager.PERMISSION_DENIED){
                //퍼미션을 요청하는 다이얼로그를 보여주기

                String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,100);

                return;
            }

        }

        //SDcard의 특정위치에 저장하기
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        if(path!=null)tv.setText(path.getAbsolutePath());

        File file = new File(path,"Data.txt");

        try {
            FileWriter fw = new FileWriter(file,true);
            PrintWriter writer = new PrintWriter(fw);

            writer.println(et.getText().toString());
            writer.flush();
            writer.close();

            Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }//clickBtn method


    //requestPermissions()메소드로 보여준 다이얼로그 선택이
    //완료되면 자동으로 실행되는 메소드


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode){

            case 100:

                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    Toast.makeText(this, "외부 저장소 쓰기 가능", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "외부 저장소 쓰기 금지", Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }
}
