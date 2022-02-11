package com.rtw181204.ex19listview;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    ListView listView;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView= findViewById(R.id.listview);

        //리스트뷰가 보여줄 View객체를 자동으로 만들어주는
        //작업을 수행하는 Adapter객체를 생성

        adapter=ArrayAdapter.createFromResource(this,R.array.datas,R.layout.listview_item);

        //adapter를 리스튜뷰에 설정해주기
        listView.setAdapter(adapter);


        //리스트뷰의 항목(item)을 클릭하는 것을 듣는
        //리스너 객체 생성 및 설정

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //대량의 데이터 배열 res폴더에 있음

                //res폴더 창고관리자 소환
                Resources res =getResources();

                //창고관리자(res)에게 arrays.xml문서 안에 있는
                //datas라는 이름의 배열객체 얻어오기

                String[] arr = res.getStringArray(R.array.datas);
                Toast.makeText(MainActivity.this,arr[position],Toast.LENGTH_SHORT).show();

            }
        });


        //리스트뷰의 항목(item)을 롱-클릭하는 것을 듣는 리스너 추가

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

               Toast.makeText(MainActivity.this,"Long click", Toast.LENGTH_SHORT).show();

               //뒤따르는 onClick이벤트를 발생시키지 않으려면
                //return을 true로
                return true;
            }
        });
    }
}
