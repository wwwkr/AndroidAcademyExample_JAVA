package com.rtw181204.ex49toolbardrawer2;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    ActionBarDrawerToggle drawerToggle;

    TextView tv;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //액티비티의 화면 뷰 참조하기!
        tv= findViewById(R.id.tv);

        //툴바를 액션바로 설정하기
        Toolbar toolbar= findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        drawerLayout= findViewById(R.id.layout_drawer);
        navigationView= findViewById(R.id.nav);
        navigationView.setItemIconTintList(null);

        drawerToggle= new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        actionBar= getSupportActionBar();
        actionBar.setTitle("MainActivity");
    }

    //액션바에 붙는 메뉴(옵션메뉴) 만들기위해 자동 호출되는 메소드
    //액티비티가 처음 실행될때 자동으로 호출됨
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_kk:
                tv.setText("kk");
                break;
            case R.id.menu_ss:
                tv.setText("ss");
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
