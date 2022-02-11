package com.rtw181204.test13;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnv= findViewById(R.id.bnv);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch ( menuItem.getItemId() ){
                    case R.id.menu_aa:
                        findViewById(R.id.container).setBackgroundColor(Color.CYAN);
                        break;
                    case R.id.menu_bb:
                        findViewById(R.id.container).setBackgroundColor(Color.MAGENTA);
                        break;
                    case R.id.menu_cc:
                        findViewById(R.id.container).setBackgroundColor(Color.GREEN);
                        break;
                    case R.id.menu_dd:
                        findViewById(R.id.container).setBackgroundColor( getResources().getColor(R.color.colorPrimary) );
                        break;
                }

                //리턴값이 true가 아니면 선택은 되지만 선택효과가 이동하지 않음.
                return true;
            }
        });
    }
}
