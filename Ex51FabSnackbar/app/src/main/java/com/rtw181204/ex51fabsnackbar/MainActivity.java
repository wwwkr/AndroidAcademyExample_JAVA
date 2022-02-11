package com.rtw181204.ex51fabsnackbar;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickFab(View view) {


        //토스트랑 비슷한 녀석(다른점 : 버튼같은 액션을 추가할 수 있음)
        Snackbar snackbar = Snackbar.make(view,"click!!",Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Submit", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click Submit", Toast.LENGTH_SHORT).show();
            }
        });


        snackbar.show();
    }
}
