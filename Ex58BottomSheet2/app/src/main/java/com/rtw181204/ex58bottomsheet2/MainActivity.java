package com.rtw181204.ex58bottomsheet2;

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


        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);

        View v = getLayoutInflater().inflate(R.layout.bottom_dialog,null);
        bottomSheetDialog.setContentView(v);

        bottomSheetDialog.setCanceledOnTouchOutside(true);


        bottomSheetDialog.show();

        ListView listView = v.findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] datas= getResources().getStringArray(R.array.datas);
                Toast.makeText(MainActivity.this, datas[position], Toast.LENGTH_SHORT).show();

                bottomSheetDialog.dismiss();
            }
        });


    }
}
