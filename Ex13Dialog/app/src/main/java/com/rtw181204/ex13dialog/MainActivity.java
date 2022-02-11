package com.rtw181204.ex13dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    String[] items = new String[]{"Apple","Banana","Orange"};
    boolean[] checked = new boolean[]{true,false,true};

    EditText dialogEt;
    TextView dialogTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBtn(View view) {

        //AlertDialog 객체 만들기

        //다이얼로그는 직접 생성할 수 없고..
        //다이얼로그를 만들어주는 건축가(Builder)객체에게 의뢰해야함.

        AlertDialog.Builder builder =new AlertDialog.Builder(this);

        //건축가에게 다이얼로그의 모양설계를 의뢰
        builder.setTitle("Dialog");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setMessage("다이얼로그 테스트입니다.");

        //버튼은 최대 3개까지 가능하며 각 버튼마다 고유의 이름이 있음
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"OK",Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"CANCEL",Toast.LENGTH_SHORT).show();
            }
        });

        //위에는 설계만 한 것

        //건축가에게 다이얼로그객체를 만들어달라고 요청
        AlertDialog dialog = builder.create();

        //다이얼로그의 바깥쪽을 터치했을 때 기본은 취소됌
        //취소되지 않도록 설정할 수 있음

        dialog.setCanceledOnTouchOutside(false);
        //dialog.setCancelable(false);
        dialog.show();



    }

    public void clickBtn2(View view) {


        AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setTitle("목록 다이얼로그");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,items[which],Toast.LENGTH_SHORT).show();

            }
        });

        builder.create().show();
    }



    public void clickBtn3(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("SingleChoice 다이얼로그");


        // 두번째 파라미터 : 다이얼로그가 보일 때 처음 선택된 RadioButton에 index번호
        builder.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(MainActivity.this,items[which],Toast.LENGTH_SHORT).show();

            }
        });

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }




    public void clickBtn4(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("MultipleChoice 다이얼로그");

       builder.setMultiChoiceItems(items, checked, new DialogInterface.OnMultiChoiceClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which, boolean isChecked) {

               checked[which]=isChecked;
           }
       });

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String s="";
                for(int i =0; i<checked.length;i++){
                    s+=checked[i];
                }

                Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();
    }

    public void clickBtn5(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("CustomView 다이얼로그");

        //사용자가 원하는 뷰를 만들어서 다이얼로그에 보여주기

        //res폴더안에 layout폴더안에 있는 dialog.xml을
        //뷰객체로 생성(부풀리다)시켜주는 객체를
        //운영체제대리인(Context)으로 부터 얻어오기

        LayoutInflater inflater =getLayoutInflater();

        View layout = inflater.inflate(R.layout.dialog,null);

        dialogEt= layout.findViewById(R.id.dialog_et);
        dialogTv= layout.findViewById(R.id.dialog_tv);
        builder.setView(layout);

        builder.setPositiveButton("확인",null);

        builder.create().show();

    }

    //다이얼로그안에 있는 버튼의 onClick에 반응하는 메소드

    public void clickDialogBtn(View v){


        //MainActivity가 보여주는 activity_main.xml안에
        //"R.id.dialog_et" 아이디값이 없음
        //만약 아래처럼 find를 하면 실행할때 에러남남
       //EditText et = findViewById(R.id.dialog_et);

        //EditText를 가지고 있는 dialog.xml의 LinearLayout에게
        //EditText를 찾아달라고 할것임
        //이 작업은 inflate될때 해야함

        dialogTv.setText(dialogEt.getText().toString());
    }
}
