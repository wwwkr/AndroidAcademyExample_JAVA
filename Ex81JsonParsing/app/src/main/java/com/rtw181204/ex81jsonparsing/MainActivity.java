package com.rtw181204.ex81jsonparsing;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);

    }

    public void clickBtn(View view) {
        //assets폴더의 파일을 가져오기위해 창고관리자 얻어오기
        AssetManager assetManager = getAssets();

        //assets/jsons/test.json파일을 읽기위한 InputStream
        try {
            InputStream is = assetManager.open("jsons/test.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();

            while(line!=null){
                buffer.append(line+"\n");

                line = reader.readLine();
            }

            String jsonData = buffer.toString();
            //jsonData가 배열이라면 JSONArray 객체로 처리해야함
            JSONArray jsonArray = new JSONArray(jsonData);

            StringBuffer buffer1 =new StringBuffer();
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String name = jsonObject.getString("name");
                String msg = jsonObject.getString("msg");
                int age = jsonObject.getInt("age");

                JSONObject joUser = jsonObject.getJSONObject("user");
                String id = joUser.getString("id");
                String pw = joUser.getString("pw");

                buffer1.append("이름:"+name +", 메세지:"+msg+", 나이:"+age+"\n");


            }
            tv.setText(buffer1.toString());


            //json문자열을 분석하기 위해 JsonObject생성
//            JSONObject jsonObject = new JSONObject(jsonData);
//
//            String name = jsonObject.getString("name");
//            String msg = jsonObject.getString("msg");
//            int age = jsonObject.getInt("age");
//
//            tv.setText("이름:"+name +", 메세지:"+msg+", 나이:"+age);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
