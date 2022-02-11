package com.rtw181204.ex20listview2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ListView listView;
    //Adapter 참조변수
    ArrayAdapter adapter;

    //대량의 데이터
    ArrayList<String> datas=new ArrayList<String>();


    EditText et;
    TextView tv_nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView= findViewById(R.id.listview);

        //테스트 목적으로 데이터들을 추가
        //(실제앱에서는 이런 데이터들을 서버 또는 데이터베이스에서 얻어옴)
        datas.add(new String("aaa"));
        datas.add(new String("bbb"));
        datas.add("ccc");

        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,datas);

        //리스트뷰에 Adapter설정
        listView.setAdapter(adapter);

        //리스트뷰의 아이템을 롱-클릭했을 때 그 항목을 삭제
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                datas.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        et=findViewById(R.id.et);

        tv_nodata= findViewById(R.id.tv_nodata);
        listView.setEmptyView(tv_nodata);

    }

    public void clickBtn(View view) {

        //EditText에 써있는 글씨를 대량의 데이터 'datas'에 추가하여
        //리스트뷰에 보이도록

        String str = et.getText().toString();
        datas.add(str);

        //명시적으로 Adapter의 데이터가 변경되었다고 공지해줘야함
        adapter.notifyDataSetChanged();
        et.setText("");

    }}
