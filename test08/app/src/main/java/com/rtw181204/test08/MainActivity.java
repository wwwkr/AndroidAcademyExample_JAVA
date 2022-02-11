package com.rtw181204.test08;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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

        et = findViewById(R.id.et);
        tv = findViewById(R.id.tv);




    }


    public void clickSave(View view) {

        String state = Environment.getExternalStorageState();

        if(!state.equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(this, "SDcard is not mounted", Toast.LENGTH_SHORT).show();
            return;
        }

        String data = et.getText().toString();
        et.setText("");

        File path;

        File[] dirs;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            dirs = getExternalFilesDirs("MyDir");
        }else{
            dirs = ContextCompat.getExternalFilesDirs(this,"MyDir");
        }

        path= dirs[0];

        tv.setText(path.getAbsolutePath());

        File file = new File(path,"Data.txt");

        try {
            FileWriter fw =new FileWriter(file,true);
            PrintWriter writer = new PrintWriter(fw);

            writer.println(data);
            writer.flush();
            writer.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }

    }

    public void clickLoad(View view) {

        String state = Environment.getExternalStorageState();

        if(state.equals(Environment.MEDIA_MOUNTED)||
                state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){

            File path;
            File[] dirs;

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){

                dirs = getExternalFilesDirs("MyDir");

            }else{
                dirs = ContextCompat.getExternalFilesDirs(this,"MyDir");
            }

            path = dirs[0];


            File file = new File(path,"Data.txt");


            try {
                FileReader fr = new FileReader(file);

                BufferedReader reader = new BufferedReader(fr);

                StringBuffer buffer = new StringBuffer();

                String line =  reader.readLine();

                while(line!=null){

                    buffer.append(line+"\n");
                    line = reader.readLine();
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

    public void clickBtn(View view) {

        String state = Environment.getExternalStorageState();

        if(!state.equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(this, "외부저장소없음", Toast.LENGTH_SHORT).show();
            return;
        }


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){


             int checkSelfPermission=checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

             if(checkSelfPermission == PackageManager.PERMISSION_DENIED){

                 String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                 requestPermissions(permissions, 100);

                 return;
             }

        }


        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        if(path!=null)tv.setText(path.getAbsolutePath());

        File file = new File(path,"Data.txt");

        try {
            FileWriter fw = new FileWriter(file,true);
            PrintWriter writer= new PrintWriter(fw);

            writer.println(et.getText().toString());
            writer.flush();
            writer.close();

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();

        }


    }//clickBtn Method

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);



        switch (requestCode){

            case 100:

                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "외부 저장소 쓰기 가능", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "외부 저장소 쓰기 금지", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}


