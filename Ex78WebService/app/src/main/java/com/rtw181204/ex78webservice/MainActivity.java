package com.rtw181204.ex78webservice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        iv = findViewById(R.id.iv);


    }

    public void clickBtn(View view) {
        //서버에서 제공하는 텍스트파일 읽어오기
        //네트워크 작업은 별도의 Thread가 해야만 함

        new Thread(){
            @Override
            public void run() {
                //서버에 있는 텍스트파일의 경로
                String serverAddress = "http://wwwkr.dothome.co.kr/index.html";

                //서버와 연결하는 무지개로드(Stream)를 열어주는 해임달(URL) 객체
                try {
                    URL url = new URL(serverAddress);

                    //무지개로드열기
                    InputStream is = url.openStream();//바이트스트림
                    InputStreamReader isr = new InputStreamReader(is);//문자스트림
                    BufferedReader reader = new BufferedReader(isr);//보조문자스트림

                    final StringBuffer buffer = new StringBuffer();
                    String line = reader.readLine(); //한줄씩읽기
                    while (true){

                        buffer.append(line +"\n");

                        line = reader.readLine();

                        if(line==null)break;
                    }

                    //읽어온 글씨를 텍스트뷰에 보여주기
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tv.setText(buffer.toString());
                        }
                    });

                    //MTML을 XML파서처럼 파싱하고 싶다면
                    //Jsoup라는 라이브러리를 사용
                    //검색해서 각자도전



                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();


    }

    public void clickBtn2(View view) {
        //서버에 있는 이미지파일 읽어오기

        String serverAddress = "http://wwwkr.dothome.co.kr/img05.jpg";
        Glide.with(this).load(serverAddress).into(iv);

//        new Thread(){
//            @Override
//            public void run() {
//                String serverAddress = "http://wwwkr.dothome.co.kr/img05.jpg";
//
//                try {
//                    URL url = new URL(serverAddress);
//
//                    InputStream is = url.openStream();
//
//                    final Bitmap bm = BitmapFactory.decodeStream(is);
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            iv.setImageBitmap(bm);
//                        }
//                    });
//
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }.start();


    }
}
