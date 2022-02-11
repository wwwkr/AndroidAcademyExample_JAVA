package com.rtw181204.ex42viewpager;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //대량의 데이터
    int[] datas = new int[]{
            R.drawable.gametitle_01,
            R.drawable.gametitle_02,
            R.drawable.gametitle_03,
            R.drawable.gametitle_04,
            R.drawable.gametitle_05,
            R.drawable.gametitle_06,
            R.drawable.gametitle_07,
            R.drawable.gametitle_08,
            R.drawable.gametitle_09,
            R.drawable.gametitle_10
    };

    //ViewPager참조변수(AdapterView)
    ViewPager pager;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager=findViewById(R.id.pager);

        adapter = new MyAdapter(datas,getLayoutInflater());

        pager.setAdapter(adapter);


        //페이지전환시에 효과주기(필수아님)
        //페이지가 조금이라도 움직일때 마다 자동으로 발동하는 메소드를 보유한 리스너 추가

        pager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View view, float position) {

                view.setRotationY(position*90);
                view.setScaleX((1-Math.abs(position))/2+0.5f);
                view.setScaleY((1-Math.abs(position))/2+0.5f);
                view.setAlpha(1-Math.abs(position));
            }
        });
    }

    public void clickNext(View view) {
        //다음페이지로 이동
        int index = pager.getCurrentItem();
        pager.setCurrentItem(index+1,true);

    }

    public void clickPrev(View view) {

        int index = pager.getCurrentItem();
        pager.setCurrentItem(index-1,true);
    }
}
