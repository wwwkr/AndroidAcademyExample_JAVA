package com.rtw181204.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    MyAdapter adapter;
    ListView listview;
    ArrayList<Member> members = new ArrayList<Member>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview=findViewById(R.id.listview);

        members.add(new Member(R.drawable.australia,"가가가","오스트레일리아"));
        members.add(new Member(R.drawable.australia+1,"나나나","벨기아"));
        members.add(new Member(R.drawable.australia+2,"다다다","캐나다"));
        members.add(new Member(R.drawable.australia+3,"라라라","중국"));
        members.add(new Member(R.drawable.australia+4,"마마마","프랑스"));
        members.add(new Member(R.drawable.australia,"가가가","오스트레일리아"));
        members.add(new Member(R.drawable.australia+1,"나나나","벨기아"));
        members.add(new Member(R.drawable.australia+2,"다다다","캐나다"));
        members.add(new Member(R.drawable.australia+3,"라라라","중국"));
        members.add(new Member(R.drawable.australia+4,"마마마","프랑스"));
        members.add(new Member(R.drawable.australia,"가가가","오스트레일리아"));
        members.add(new Member(R.drawable.australia+1,"나나나","벨기아"));
        members.add(new Member(R.drawable.australia+2,"다다다","캐나다"));
        members.add(new Member(R.drawable.australia+3,"라라라","중국"));
        members.add(new Member(R.drawable.australia+4,"마마마","프랑스"));


        LayoutInflater inflater = getLayoutInflater();

        adapter =new MyAdapter(members,inflater);


        listview.setAdapter(adapter);


    }
}
