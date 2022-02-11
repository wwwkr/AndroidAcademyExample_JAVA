package com.rtw181204.ex23listviewcustom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    //Adapter참조변수
    MyAdapter adapter;

    //대량의 데이터
    ArrayList<Member> members = new ArrayList<Member>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        members.add(new Member(R.drawable.korea,"전현무","대한민국"));
        members.add(new Member(R.drawable.canada,"기욤패트리","캐나다"));
        members.add(new Member(R.drawable.china,"장위안","중국"));
        members.add(new Member(R.drawable.usa,"타일러","미국"));
        members.add(new Member(R.drawable.italy,"알베르토","이탈리아"));

        members.add(new Member(R.drawable.korea,"전현무","대한민국"));
        members.add(new Member(R.drawable.canada,"기욤패트리","캐나다"));
        members.add(new Member(R.drawable.china,"장위안","중국"));
        members.add(new Member(R.drawable.usa,"타일러","미국"));
        members.add(new Member(R.drawable.italy,"알베르토","이탈리아"));

        members.add(new Member(R.drawable.korea,"전현무","대한민국"));
        members.add(new Member(R.drawable.canada,"기욤패트리","캐나다"));
        members.add(new Member(R.drawable.china,"장위안","중국"));
        members.add(new Member(R.drawable.usa,"타일러","미국"));
        members.add(new Member(R.drawable.italy,"알베르토","이탈리아"));


        listView=findViewById(R.id.listview);
        //대량의 데이터를 뷰객체들로 자동으로 만들어주는 adapter객체를 생성
        LayoutInflater inflater = getLayoutInflater();
        adapter=new MyAdapter(members, inflater);


        listView.setAdapter(adapter);





    }
}
