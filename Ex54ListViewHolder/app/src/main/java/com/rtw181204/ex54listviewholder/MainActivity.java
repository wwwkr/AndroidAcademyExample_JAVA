package com.rtw181204.ex54listviewholder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items = new ArrayList<>();
    ListView listView;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        items.add(new String("aaa"));
        items.add(new String("bbb"));
        items.add(new String("ccc"));
        items.add(new String("ddd"));
        items.add(new String("eee"));
        items.add(new String("aaa"));
        items.add(new String("bbb"));
        items.add(new String("ccc"));
        items.add(new String("ddd"));
        items.add(new String("eee"));
        items.add(new String("aaa"));
        items.add(new String("bbb"));
        items.add(new String("ccc"));
        items.add(new String("ddd"));
        items.add(new String("eee"));
        items.add(new String("aaa"));
        items.add(new String("bbb"));
        items.add(new String("ccc"));
        items.add(new String("ddd"));
        items.add(new String("eee"));

        listView = findViewById(R.id.listview);
        adapter = new MyAdapter(items,getLayoutInflater());

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
