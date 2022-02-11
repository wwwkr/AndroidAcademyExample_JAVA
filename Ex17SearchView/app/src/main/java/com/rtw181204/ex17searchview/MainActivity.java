package com.rtw181204.ex17searchview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    android.support.v7.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //onCreate()메소드 후에 자동으로 실행되는 메소드
    //액션바에 메뉴를 달고 싶다면 무조건 이 메소드를 오버라이드해야함
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.actionbar, menu);

        //menu객체로 부터 SearchView를 가지고 있는
        // MenuItem객체를 얻어오기!!
        MenuItem item= menu.findItem(R.id.menu_search);
        // MenuItem으로 부터 actionViewClass로 지정된
        // SearchView를 얻어오기
        searchView= (android.support.v7.widget.SearchView)item.getActionView();

        //힌트 적용하기
        searchView.setQueryHint("input word");

        //서치뷰에 작성한 글씨를 얻어오기...
        //서치뷰에 글씨를 변경할때 마다 자도으로 호출되는 리스너
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            //서치뷰에 글씨 작성할 때 사용하는 소프트키패드에 돋보기모양의
            //'완료' 버튼이 있음... 이것을 누르면 발동하는 메소드..
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(MainActivity.this, s+" 를 검색합니다.", Toast.LENGTH_SHORT).show();

                //서치뷰 글씨 지우기
                searchView.setQuery("", true);
               searchView.setIconified(true);//돋보기모양으로 바꾸기
                return false;
            }

            //글씨가 바뀔때 마다 실행되는 메소드..
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
}
