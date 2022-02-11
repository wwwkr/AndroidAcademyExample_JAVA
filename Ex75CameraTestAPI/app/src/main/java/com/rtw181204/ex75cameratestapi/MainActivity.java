package com.rtw181204.ex75cameratestapi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    CameraView cv;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cv = findViewById(R.id.cv);
        iv = findViewById(R.id.iv);

        //동적퍼미션
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int permissionResult1 = checkSelfPermission(Manifest.permission.CAMERA);
            int permissionResult2 = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);


            if(permissionResult1==PackageManager.PERMISSION_DENIED||permissionResult2==PackageManager.PERMISSION_DENIED){

                String[] permissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,10);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case 10:
                if(grantResults[0]==PackageManager.PERMISSION_DENIED || grantResults[1]==PackageManager.PERMISSION_DENIED){

                    Toast.makeText(this, "이 앱 사용불가", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "사용 가능", Toast.LENGTH_SHORT).show();
                }
                break;
        }


    }

    public void clickCapture(View view) {

        //오토포커싱기능을 먼저 실행함
        cv.camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                //첫번째 파라미터 : 포커스성공여부

                Toast.makeText(MainActivity.this, ""+success, Toast.LENGTH_SHORT).show();
            }
        });


        cv.camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                //캡쳐된 데이터(byte[])를 Bitmap객체로 생성
                Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);

                iv.setImageBitmap(bm);
                //FileOutputStream fos = openFileOutput("aa.jpg",MODE_PRIVATE);

                //외부메모리 : 공용영역
                File path = Environment.getExternalStorageDirectory();

                //파일명.확장자
                String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File file = new File(path,"IMG_"+time+".jpg");

                //만들어진 File에 byte[] 이미지데이터 저장하기
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(data);
                    fos.flush();
                    fos.close();

                    Toast.makeText(MainActivity.this, "saved", Toast.LENGTH_SHORT).show();

                    //저장은 되지만 Gallery앱에서 이를 모름
                    //Gallery앱이 스캔하도록 방송하기
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.parse("file://"+file.getPath()));
                    sendBroadcast(intent);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //캡쳐하면 프리뷰가 멈춤
                cv.camera.startPreview();

            }
        });

    }
}
