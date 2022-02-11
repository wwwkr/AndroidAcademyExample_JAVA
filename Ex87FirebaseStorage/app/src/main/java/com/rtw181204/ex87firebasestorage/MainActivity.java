package com.rtw181204.ex87firebasestorage;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ImageView iv;

    Uri imgUri; //갤러리앱에서 선택한 이미지의 uri

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = findViewById(R.id.iv);
    }

    public void clickLoad(View view) {

        //Firebase 저장소에 저장되어 있는 이미지 파일 읽어오기

        //1.FirebaseStorage 관리객체 얻어오기
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        //2.최상위노드 참조객체 얻어오기
        StorageReference rootRef = firebaseStorage.getReference();

        //읽어오길 원하는 파일의 참조객체 얻어오기
        StorageReference imgRef = rootRef.child("a_frog.png");
        imgRef = rootRef.child("pictures/a_ele.png");

        imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(MainActivity.this).load(uri).into(iv);
            }
        });
    }

    public void clickSelect(View view) {
        //사진선택 앱(갤러리앱, 사진앱 등)을 실행시키고 그 결과 받기
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 10);

    }

    //갤러리앱의 선택결과를 돌려받을 때 자동으로 실행되는 콜백메소드


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case 10:

                if(resultCode==RESULT_OK){
                    imgUri = data.getData();
                    Glide.with(this).load(imgUri).into(iv);
                }

                break;
        }
    }

    public void clcikSave(View view) {

        if(imgUri==null)return;

        //FirebaseStorage 관리객체 얻어오기
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        //저장할 파일명으로 노드참조객체 생성
        //중복되지 않는 이름으로 파일명을 생성하기 위해 날짜이용
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String fileName = sdf.format(new Date()) + ".png"; //오늘 날짜로 만들어라


        StorageReference imgRef = firebaseStorage.getReference("uploads/"+fileName);

        //노드 참조객체(imgRef)에 파일 업로드하기
        UploadTask uploadTask =imgRef.putFile(imgUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(MainActivity.this, "upload", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
