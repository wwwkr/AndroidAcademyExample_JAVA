package com.rtw181204.ex50appbartablayout;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager pager;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //툴바를 액션바로 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //탭버튼(Tab) 객체 추가하기
        tabLayout = findViewById(R.id.layout_tab);
//        //탭(tab)객체 생성
//        TabLayout.Tab tab = null;
//
//        tab = tabLayout.newTab();
//        tab.setTag("tab1"); //식별자 - id의 역할
//        tab.setText("Home"); //탭에 보여지는 글씨
//        tab.setIcon(R.mipmap.ic_launcher);
//        tabLayout.addTab(tab);
//
//        tab = tabLayout.newTab();
//        tab.setTag("tab2"); //식별자 - id의 역할
//        tab.setText("Theme"); //탭에 보여지는 글씨
//        tab.setIcon(R.mipmap.ic_launcher);
//        tabLayout.addTab(tab);
//
//        //한번에 탭설정가능
//        tab = tabLayout.newTab().setTag("tab3").setText("Talk").setIcon(R.mipmap.ic_launcher);
//        tabLayout.addTab(tab);

        pager = findViewById(R.id.pager);
        adapter = new MyAdapter(getSupportFragmentManager());


        pager.setAdapter(adapter);


        //뷰페이저와 TabLayout을 연동하기
        tabLayout.setupWithViewPager(pager);

        //액션바에 서브타이틀 주기

        getSupportActionBar().setSubtitle("HOME");


        //탭이 변경되었을 때 서브타이틀 변경
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getSupportActionBar().setSubtitle(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //아이콘을 주려면 별도의 추가작업 필요
        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher);

        //뷰페이저와 연동한 TabLayout은 페이지의 개수만큼
        //자동으로 Tab버튼을 생성하여 추가함
        //즉, 저 위 코드처럼 별도로 Tab버튼을 추가하는 코드를
        //작성하지 않음, 작성해도 무의미

//        //탭버튼 선택시에 뷰페이저의 페이지 변경
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//                String tag = tab.getTag().toString();
//
//                if(tag.equals("tab1")){
//
//                    pager.setCurrentItem(0,true);
//
//                }else if(tag.equals("tab2")){
//
//                    pager.setCurrentItem(1,true);
//
//                }else if(tag.equals("tab3")){
//
//                    pager.setCurrentItem(2,true);
//
//                }
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//        //뷰페이저의 페이지가 변경되었을때 선택변경
//        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//              tabLayout.getTabAt(position).select();
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case  R.id.menu_search:
                Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
                break;

            case  R.id.menu_aa:
                Toast.makeText(this, "aa", Toast.LENGTH_SHORT).show();
                break;

            case  R.id.menu_bb:
                Toast.makeText(this, "bb", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
