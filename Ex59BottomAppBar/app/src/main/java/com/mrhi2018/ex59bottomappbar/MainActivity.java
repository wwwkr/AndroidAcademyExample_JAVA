package com.mrhi2018.ex59bottomappbar;

import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    BottomAppBar bab;
    boolean isCenter= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BAB를 액션바로 대체하기
        bab= findViewById(R.id.bab);
        setSupportActionBar(bab);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bab_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void clickBtn(View view) {
        isCenter= !isCenter;

        if(isCenter) bab.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        else  bab.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);

        Snackbar snackbar= Snackbar.make(findViewById(R.id.aa), "aaa", Snackbar.LENGTH_LONG);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)snackbar.getView().getLayoutParams();
        params.setMargins(0,0,0,250);

        snackbar.getView().setLayoutParams(params);


        snackbar.show();
    }
}
