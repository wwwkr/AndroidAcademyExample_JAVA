package com.rtw181204.test03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {


    ListView listView;

    MyAdapter adapter;

    ArrayList<Member> members = new ArrayList<Member>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView= findViewById(R.id.listview);

        members.add(new Member(R.drawable.australia+0,"Title01","9:00","DRAMA","1930"));
        members.add(new Member(R.drawable.australia+1,"Title02","9:01","DRAMA","1931"));
        members.add(new Member(R.drawable.australia+2,"Title03","9:02","DRAMA","1932"));
        members.add(new Member(R.drawable.australia+3,"Title04","9:03","DRAMA","1933"));
        members.add(new Member(R.drawable.australia+4,"Title05","9:04","DRAMA","1934"));
        members.add(new Member(R.drawable.australia+5,"Title06","9:05","DRAMA","1935"));
        members.add(new Member(R.drawable.australia+6,"Title07","9:06","DRAMA","1936"));


        LayoutInflater inflater = getLayoutInflater();

        adapter= new MyAdapter(members,inflater);

        listView.setAdapter(adapter);

    }
}
