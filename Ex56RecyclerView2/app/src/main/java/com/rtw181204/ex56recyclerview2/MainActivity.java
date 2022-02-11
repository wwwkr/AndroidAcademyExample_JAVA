package com.rtw181204.ex56recyclerview2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> items = new ArrayList<>();

    RecyclerView recyclerView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //데이터 추가작업
        items.add(new Item("루피","해적단 선장",R.drawable.ch_luffy,R.drawable.img01));
        items.add(new Item("조로","해적단 검사",R.drawable.ch_zoro,R.drawable.img02));
        items.add(new Item("나미","해적단 항해사",R.drawable.ch_nami,R.drawable.img03));
        items.add(new Item("우솝","해적단 저격수",R.drawable.ch_usoup,R.drawable.img04));
        items.add(new Item("상디","해적단 요리사",R.drawable.ch_sandi,R.drawable.img06));
        items.add(new Item("쵸파","해적단 의사",R.drawable.ch_chopa,R.drawable.img07));


        recyclerView = findViewById(R.id.recycler);
        adapter= new MyAdapter(items,this);
        recyclerView.setAdapter(adapter);


        //리사이클러뷰의 아이템클릭리스너가 없음
        //항목 하나 itemView에게 직접 클릭리스너를 추가해야함
        //이 작업은 MyAdapter.java에서 할수밖에없음

        //리사이클러 및 아이템 뷰 꾸미기
        MyDecoration myDecoration = new MyDecoration();
        recyclerView.addItemDecoration(myDecoration);



    }

    public void clickAdd(View view) {
        items.add(0,new Item("NEW","message",R.drawable.img12,R.drawable.img11));
        //아답터에게 데이터가 바꼈다는걸 공지
        adapter.notifyItemInserted(0);
        //리사이클러뷰의 커서위치 변경
        recyclerView.scrollToPosition(0);
    }

    public void clickDel(View view) {
        if(items.size()==0)return;

        items.remove(0);
        adapter.notifyItemRemoved(0);
    }

    public void clickBtn(View view) {
        GridLayoutManager manager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(manager);
    }

    public void clickBtn2(View view) {

        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(manager);
    }
}
