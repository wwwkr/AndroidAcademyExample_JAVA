package com.mrhi2018.ex47navigationdrawer;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout= findViewById(R.id.layout_drawer);
        navigationView= findViewById(R.id.navi);

        //item icon색조를 적용하지 않음. 설정안하면 기본 회색색조
        navigationView.setItemIconTintList(null);

        //네비게이션뷰의 메뉴아이템을 선택했을때 반응하는 리스너
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch ( menuItem.getItemId() ){
                    case R.id.menu_gallery:
                        Toast.makeText(MainActivity.this, "gallery", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_send:
                        Toast.makeText(MainActivity.this, "send", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_aa:
                        Toast.makeText(MainActivity.this, "aa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_bb:
                        Toast.makeText(MainActivity.this, "bb", Toast.LENGTH_SHORT).show();
                        break;
                }

                //Drawer를 닫기..
                drawerLayout.closeDrawer(navigationView);

                return false;
            }
        });

        //드로우어 조절용 토글버튼 생성
        drawerToggle= new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);

        //드로우어 토글버튼아이콘이 보이도록 액션바에게 요청
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //삼선아이콘 모양으로 보이도록 토글버튼의 동기 맞추기..
        drawerToggle.syncState();

        //삼선아이콘과 화살표아이콘이 자동 변환되도록..
        drawerLayout.addDrawerListener(drawerToggle);
    }

    //토글버튼을 눌렀을때 자동으로 드로우어가 열리고 닫히도록..
    //토글버튼의 위치가 액션바에 있다보니...
    //액티비티 입장에서는 토글버튼도 액션바메뉴라고 보고 있음..
    //아이템클릭상황을 토글버튼에 전달해주기!!!
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        drawerToggle.onOptionsItemSelected(item);

        return super.onOptionsItemSelected(item);
    }
}
