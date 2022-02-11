package com.rtw181204.test05;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ListView listView;

    MyAdapter adapter;

    ArrayList<Member> members = new ArrayList<Member>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);

        LayoutInflater inflater = getLayoutInflater();

        adapter = new MyAdapter(inflater,members);


        listView.setAdapter(adapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case 10:

                if(resultCode==RESULT_OK){

                    members.add(new Member(data.getStringExtra("name"),data.getStringExtra("nick"),data.getStringExtra("title"),data.getStringExtra("content")));


                    adapter.notifyDataSetInvalidated();
        }

        }

    }

    public void clickAdd(View view) {

        Intent intent = new Intent(this,SecondActivity.class);

        startActivityForResult(intent,10);

    }
}
