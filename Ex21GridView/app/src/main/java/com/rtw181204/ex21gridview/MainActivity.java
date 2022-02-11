package com.rtw181204.ex21gridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ArrayAdapter adapter;

    //대량의 데이터들
    ArrayList<String> datas = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //테스트 목적으로 데이터 추가
        datas.add(new String("aaa"));
        datas.add(new String("bbb"));
        datas.add(new String("ccc"));
        datas.add(new String("ddd"));
        datas.add(new String("eee"));
        datas.add(new String("fff"));
        datas.add(new String("ggg"));
        datas.add(new String("hhh"));



        gridView=findViewById(R.id.gridview);

        //adapter객체 생성

        adapter=new ArrayAdapter(this,R.layout.gridview_item,datas);
        gridView.setAdapter(adapter);
    }
}
