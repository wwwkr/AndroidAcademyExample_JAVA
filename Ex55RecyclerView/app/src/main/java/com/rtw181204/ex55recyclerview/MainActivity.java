package com.rtw181204.ex55recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> datas = new ArrayList<>();

    MyAdapter adapter;

    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        datas.add(new String("aaaa"));
        datas.add(new String("bbbb"));
        datas.add(new String("cccc"));
        datas.add(new String("dddd"));
        datas.add(new String("eeee"));
        datas.add(new String("aaaa"));
        datas.add(new String("bbbb"));
        datas.add(new String("cccc"));
        datas.add(new String("dddd"));
        datas.add(new String("eeee"));
        datas.add(new String("aaaa"));
        datas.add(new String("bbbb"));
        datas.add(new String("cccc"));
        datas.add(new String("dddd"));
        datas.add(new String("eeee"));


        rv= findViewById(R.id.recycler);
        adapter = new MyAdapter(datas,getLayoutInflater());
        rv.setAdapter(adapter);

        //리사이클러뷰는 뷰(항목)들의 배치를 어떻게 할지 설정해주어야함
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);

    }
}
