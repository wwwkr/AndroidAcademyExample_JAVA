package com.rtw181204.ex47navigationdrawer2;

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


        drawerLayout = findViewById(R.id.layout_drawer);
        navigationView = findViewById(R.id.navi);

        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
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

                drawerLayout.closeDrawer(navigationView);

                return false;
            }
        });


        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.app_name,R.string.app_name);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerToggle.syncState();

        drawerLayout.addDrawerListener(drawerToggle);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        drawerToggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }
}
