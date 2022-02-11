package com.rtw181204.ex80httprequestdbtest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    EditText etName, etMsg;
    ImageView iv;

    String imgPath;//이미지의 절대경로

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName= findViewById(R.id.et_name);
        etMsg= findViewById(R.id.et_msg);
        iv= findViewById(R.id.iv);

        //외부저장소에 있는 이미지파일을 서버로 보내려면..퍼미션필요
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if( checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                //허용이 안되어 있느므로 퍼미션요청 다이얼로그 보이기
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }

    //퍼미션 요청 결과 받는 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 100:
                if(grantResults[0]==PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "외부저장소 사용불가\n이미지업로드 불가", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void clickBtn(View view) {
        //Gallery앱에서 사진 선택하여 가져오기!!
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){
                    //선택된 이미지의 URI를 얻어오기
                    Uri uri= data.getData();
                    if(uri!=null){

                        Glide.with(this).load(uri).into(iv);

                        //얻어온 파일의 Uri는 Gallery앱의 DB주소임.
                        //업로드 하려면 이미지의 절대경로가 필요함.
                        //Uri --> 절대주소(String)로 변환!!
                        imgPath= getRealPathFromUri(uri);
                        new android.app.AlertDialog.Builder(this).setMessage(imgPath).show();

                    }
                }
                break;
        }

    }


    //Uri -- > 절대경로로 바꿔서 리턴시켜주는 메소드
    String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor= loader.loadInBackground();
        int column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result= cursor.getString(column_index);
        cursor.close();
        return  result;
    }



    public void clickUpload(View view) {

        //업로드할 서버 주소
        String serverUrl="http://wwwkr.dothome.co.kr/Android/insertDB.php";

        String name= etName.getText().toString();
        String msg= etMsg.getText().toString();

        //보낼데이터 3개( name, msg, imgPath)
        //이미지가 있는 경우는 "POST"방식으로 요청함.

        //HttpUrlConnection을 사용하여 파일을 전송하는 것은 어려움.
        //Volley+ 라이브러리를 이용하고자함.

        //요청객체만들기..(소포Box만들기..보낼데이터를 넣어서..)
        SimpleMultiPartRequest multiPartRequest= new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //서버로부터 응답을 받았을때 자동 실행..
                //매개변수로 받은 String이 echo된 결과값..
                new android.app.AlertDialog.Builder(MainActivity.this).setMessage(response).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //서버요청 중 에러가 발생하면 자동 실행..
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //POST 방식으로 보낼 데이터들 요청객체에 추가하기
        multiPartRequest.addStringParam("name", name);
        multiPartRequest.addStringParam("msg", msg);
        multiPartRequest.addFile("upload", imgPath);

        //요청객체를 실제 서버쪽으로 보내기 위해 우체통같은 객체
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        //요청객체를 우체통같은 객체에 넣기
        requestQueue.add(multiPartRequest);

    }

    public void clickLoad(View view) {
        //DB에 있는 정보들을 보여주는 액티비티로 이동
        startActivity( new Intent(this, BoardActivity.class) );

    }
}

