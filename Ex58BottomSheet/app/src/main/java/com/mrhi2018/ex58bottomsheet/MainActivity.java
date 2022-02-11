package com.mrhi2018.ex58bottomsheet;

import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBtn(View view) {

        //다이얼로그 객체 생성
        final BottomSheetDialog bottomSheetDialog= new BottomSheetDialog(this);

        //보여질 View설정
        View v= getLayoutInflater().inflate(R.layout.bottom_dialog, null);
        bottomSheetDialog.setContentView(v);

        bottomSheetDialog.setCanceledOnTouchOutside(true);

        //다이얼로그 보이기
        bottomSheetDialog.show();

        //다이얼로그안에 있는 ListView의 아이템 클릭 반응하기
        ListView listView= v.findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] datas= getResources().getStringArray(R.array.datas);
                Toast.makeText(MainActivity.this, datas[position], Toast.LENGTH_SHORT).show();

                bottomSheetDialog.dismiss();

            }
        });


//        listView.addHeaderView();
//        listView.addFooterView();


    }
}
