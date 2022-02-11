package com.rtw181204.ex79httprequesttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {


    EditText etName, etMsg;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.et_name);
        etMsg = findViewById(R.id.et_msg);
        tv = findViewById(R.id.tv);


    }

    public void clickGet(View view) {

        //서버에 데이터 보내기
        new Thread(){
            @Override
            public void run() {

                String name = etName.getText().toString();
                String msg = etMsg.getText().toString();

                //Get방식으로 데이터를 보낼 서버주소
                String serverUrl = "http://wwwkr.dothome.co.kr/Android/getTest.php";

                //Get방식은 보낼 데이터(name, msg)를 URL뒤에 붙여서 보내는 방식
                //한글은 URL에 사용불가! 그래서 한글은 utf-8로 인코딩 해야함

                try {
                    name = URLEncoder.encode(name,"utf-8");
                    msg = URLEncoder.encode(msg, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //보낼 데이터를 URL뒤에 붙이기
                String getUrl = serverUrl+"?name="+name+"&msg="+msg;

                //서버와 연결작업
                try {
                    URL url = new URL(getUrl);

                    //URL은 InputStream밖에 못함
                    //InputStream, OutputStream을 모두 사용할 수 있는 HttpConnection객체 얻어오기
                    HttpURLConnection connection=  (HttpURLConnection)url.openConnection();

                    //connection의 주요설정
                    connection.setRequestMethod("GET");//반드시 대문자
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);

                    //Get방식이므로 이미 URL에 데이터를 붙여서 보넀기에
                    //별도의 OutputStream을 통해 데이터를 전송할 필요가 없음

                    //getTest.php로부터 echo로 응답(Response)된 결과 데이터를 읽어와야함
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    final StringBuffer buffer = new StringBuffer();

                    String line = reader.readLine();

                    while (line!=null){
                        buffer.append(line+"\n");
                        line = reader.readLine();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(buffer.toString());
                        }
                    });


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();


    }

    public void clickPost(View view) {


        new Thread(){
            @Override
            public void run() {

                String name = etName.getText().toString();
                String msg = etMsg.getText().toString();

                String serverUrl = "http://wwwkr.dothome.co.kr/Android/postTest.php";

                try {
                    URL url = new URL(serverUrl);

                    //Http통신규약에 따라 데이터를 주고받는 역할을 수행하는
                    //객체를 얻어오기
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);

                    //보낼 데이터
                    String postData = "name="+name+"&msg="+msg;

                    OutputStream os = connection.getOutputStream();
                    OutputStreamWriter writer = new OutputStreamWriter(os);

                    writer.write(postData, 0 , postData.length());
                    writer.flush();
                    writer.close();

                    //postTest.php로 부터 응답(Response)받기
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    final StringBuffer buffer = new StringBuffer();

                    String line = reader.readLine();
                    while (line!=null){
                        buffer.append(line+"\n");
                        line = reader.readLine();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(buffer.toString());
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }
}
