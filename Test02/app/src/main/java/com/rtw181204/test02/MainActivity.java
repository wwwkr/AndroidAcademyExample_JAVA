package com.rtw181204.test02;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    ImageView iv02,iv03,iv04,iv05;
    ImageView images_iv;

    int cnt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        iv02=findViewById(R.id.iv02);
        iv03=findViewById(R.id.iv03);
        iv04=findViewById(R.id.iv04);
        iv05=findViewById(R.id.iv05);


    }




    public void clickIv(View view) {


        switch (view.getId()){

            case R.id.iv02:
                Toast.makeText(this,iv02.getTag()+"",Toast.LENGTH_SHORT).show();
                break;

            case R.id.iv03:
                Toast.makeText(this,iv03.getTag()+"",Toast.LENGTH_SHORT).show();
                break;

            case R.id.iv04:
                Toast.makeText(this,iv04.getTag()+"",Toast.LENGTH_SHORT).show();
                break;

            case R.id.iv05:
                Toast.makeText(this,iv05.getTag()+"",Toast.LENGTH_SHORT).show();
                break;
        }

    }


    public void clickIv01(View view) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        LayoutInflater inflater = getLayoutInflater();


        View layout = inflater.inflate(R.layout.images,null);

        images_iv = layout.findViewById(R.id.images_iv);


        images_iv.setImageResource(R.drawable.moana);

        images_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cnt++;
                if(cnt==5)cnt=0;
                images_iv.setImageResource(R.drawable.moana+cnt);
            }
        });
        builder.setView(layout);


            builder.create().show();
    }
}
