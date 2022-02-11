package com.rtw181204.ex73cameratest2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    ImageView iv;

    //캡쳐한 이미지를 저장할 파일의 경로 Uri객체 참조변수
    Uri imgUri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = findViewById(R.id.iv);


        //외부메모리 사용에 대한 동적 퍼미션
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int permissionResult = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if(permissionResult==PackageManager.PERMISSION_DENIED){
                String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

                requestPermissions(permissions,100);
             }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){


            case 100:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    Toast.makeText(this, "카메라앱 사용 가능", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "카메라 기능 제한", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void clickBtn(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //이미지 경로 설정
        setImageUri();

        //추가데이터를 요청
        //캡쳐할 사진을 제공한 File의 경로를 저장해주세요
        if(imgUri!=null) intent.putExtra(MediaStore.EXTRA_OUTPUT,imgUri);
        //자동저장된 이미지는 실 사이즈의 이미지임

        startActivityForResult(intent,10);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case 10:
                if(resultCode==RESULT_OK){
                    Uri uri = null;

                    if(data!=null) uri =data.getData();

                    if(uri!=null){

                        Picasso.get().load(uri).into(iv);

                    }else{
                        Picasso.get().load(imgUri).into(iv);
                        //이미지뷰에 이미지가 보인다면
                        //파일저장이 잘 된것임
                        //다만, 갤러리앱에서 보면 없는 경우가 있음
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        intent.setData(imgUri);
                        sendBroadcast(intent);
                    }
                }
                break;
        }
    }

    //저장될 이미지의 경로 설정 메소드
    void setImageUri(){

        //구글에서는 이미지를 외부저장소(SD카드)에 저장하길 권장

        //외부 저장소는 2개의 영역 중 하나를 선택
        //1. 외부 저장소의 앱 전용영역 - 앱을 지우면 파일도 같이 지워짐
        File path = getExternalFilesDir("photo");
        //외부메모리에 본인 패키지명으로 된 폴더가 생기고
        //그 안에 files폴더가 생기고 그 안에 "photo"라는 이름의 폴더가 생기며 그 위치를 지칭함
        if(!path.exists())path.mkdirs(); //폴더가 없으면 생성

        //2. 외부 저장소의 공용영역 - 앱을 지워도 파일은 지워지지않음
        path = Environment.getExternalStorageDirectory();//외부메모리의 root(최상위)경로
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        //3. 저장할 파일의 이름을 포함한 경로 생성
        //파일명이 중복되지 않도록

         //1)날짜를 이용하는 방법
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String fileName = "IMG_"+sdf.format(new Date())+".jpg";

        File file = new File(path, fileName);

         //2)자동으로 임시파일명을 만들어주는 메소드
//        try {
//            file = File.createTempFile("IMG_",".jpg",path);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //카메라앱에 파일경로를 전달할 때는 File객체가 아니라
        //Uri객체를 변환하여 달라고 함

        //Nougat버전 전과 후가 다름
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.N){

            imgUri = Uri.fromFile(file);


        }else {

            //누가버전부터 보안강화를 위해 파일의 경로 "file://" 글자가 있는 경로는
            //다른 앱에 전달할 수 없음 그래서 Uri로 변경해서 보냈으며 이때는 Uri.fromFile()이였는데
            //보안상 금지됨 File Provider를 이용해야함

            imgUri = FileProvider.getUriForFile(this,"com.rtw181204.ex73cameratest2.FileProvider",file);

        }




    }

}
