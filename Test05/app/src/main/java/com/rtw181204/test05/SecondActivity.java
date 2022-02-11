package com.rtw181204.test05;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void clickCancle(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("작성을 취소하시겠습니까?");

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });



        builder.create().show();

    }

    public void clickOK(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("작성을 완료하시겠습니까?");

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        builder.setPositiveButton("완료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = getIntent();

                EditText tv_name = findViewById(R.id.tv_name);
                EditText tv_nick = findViewById(R.id.tv_nick);
                EditText tv_title = findViewById(R.id.tv_title);
                EditText tv_content = findViewById(R.id.tv_content);


                intent.putExtra("name", tv_name.getText().toString());
                intent.putExtra("nick", tv_nick.getText().toString());
                intent.putExtra("title", tv_title.getText().toString());
                intent.putExtra("content", tv_content.getText().toString());

                setResult(RESULT_OK,intent);

                finish();


            }
        });



        builder.create().show();
    }
}
