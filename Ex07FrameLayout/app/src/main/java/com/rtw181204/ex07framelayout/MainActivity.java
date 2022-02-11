package com.rtw181204.ex07framelayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout layout_btn, layout_text,layout_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout_btn=findViewById(R.id.layout_btn);
        layout_text=findViewById(R.id.layout_text);
        layout_img=findViewById(R.id.layout_img);
    }

    public void clickBtn(View view) {

        layout_btn.setVisibility(View.GONE);
        layout_text.setVisibility(View.GONE);
        layout_img.setVisibility(View.GONE);
        int id = view.getId();

        switch (id){

            case R.id.btn01:
                layout_btn.setVisibility(View.VISIBLE);

                break;

            case R.id.btn02:
                layout_text.setVisibility(View.VISIBLE);

                break;

            case R.id.btn03:
                layout_img.setVisibility(View.VISIBLE);

                break;
        }
    }
}
