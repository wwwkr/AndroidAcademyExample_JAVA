package com.rtw181204.numbaseball;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tv2;

    EditText et1,et2,et3;

    Button btn;


    int com1,com2,com3;
    int user1,user2,user3;
    int S=0;
    int B=0;

    int cnt=1;
    String str1,str2,str3;

    Random rnd = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        tv2=findViewById(R.id.tv2);

        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        et3=findViewById(R.id.et3);

        btn=findViewById(R.id.btn);





        int[] coms = new int[3];

        for(int i = 0; i <coms.length ;i ++){

            coms[i]= rnd.nextInt(10);
            for(int j =0 ;j <i ; j++){

                if(coms[i]==coms[j]){
                    i--;
                }
            }
        }

        com1 = coms[0];
        com2 = coms[1];
        com3 = coms[2];





            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Editable editable1 = et1.getEditableText();
                    str1 = editable1.toString();


                    Editable editable2 = et2.getEditableText();
                    str2 = editable2.toString();


                    Editable editable3 = et3.getEditableText();
                    str3 = editable3.toString();

                    if (!(str1.equals("") || str2.equals("") || str3.equals(""))) {
                        user1 = Integer.parseInt(str1);
                        user2 = Integer.parseInt(str2);
                        user3 = Integer.parseInt(str3);


                        if (com1 == user1) {
                            S++;
                        } else if (com1 == user2 || com1 == user3) {
                            B++;
                        }

                        if (com2 == user2) {
                            S++;
                        } else if (com2 == user1 || com2 == user3) {
                            B++;
                        }

                        if (com3 == user3) {
                            S++;
                        } else if (com3 == user1 || com3 == user2) {
                            B++;
                        }
                        tv2.append(cnt + "번째시도 " + str1 + str2 + str3 + " : " + S + " Strike, " + B + " Ball\n");
                        cnt++;
                        S = 0;
                        B = 0;
                        et1.setText("");
                        et2.setText("");
                        et3.setText("");


                        if (cnt == 10) {
                            tv2.append("실패");
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                            builder.setTitle("실패");
                            builder.setMessage("다시하시겠습니까?");

                            builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    tv2.setText("");
                                    cnt = 1;

                                    int[] coms = new int[3];

                                    for (int i = 0; i < coms.length; i++) {

                                        coms[i] = rnd.nextInt(10);
                                        for (int j = 0; j < i; j++) {

                                            if (coms[i] == coms[j]) {
                                                i--;
                                            }
                                        }
                                    }

                                    com1 = coms[0];
                                    com2 = coms[1];
                                    com3 = coms[2];
                                }
                            });

                            builder.setPositiveButton("취소", null);

                            builder.create().show();
                        }

                        if (com1 == user1 && com2 == user2 && com3 == user3) {

                            tv2.append("성공");

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                            builder.setTitle("성공");
                            builder.setMessage("다시하시겠습니까?");

                            builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    tv2.setText("");
                                    cnt = 1;
                                    int[] coms = new int[3];

                                    for (int i = 0; i < coms.length; i++) {

                                        coms[i] = rnd.nextInt(10);
                                        for (int j = 0; j < i; j++) {

                                            if (coms[i] == coms[j]) {
                                                i--;
                                            }
                                        }
                                    }

                                    com1 = coms[0];
                                    com2 = coms[1];
                                    com3 = coms[2];
                                }
                            });

                            builder.setPositiveButton("취소", null);


                            builder.create().show();

                        }


                    }


                }


            });




    }


    public void clickBtn(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


        builder.setTitle("");
        builder.setMessage("초기화하시겠습니까?");

        builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv2.setText("");
                cnt = 1;

                int[] coms = new int[3];

                for (int i = 0; i < coms.length; i++) {

                    coms[i] = rnd.nextInt(10);
                    for (int j = 0; j < i; j++) {

                        if (coms[i] == coms[j]) {
                            i--;
                        }
                    }
                }

                com1 = coms[0];
                com2 = coms[1];
                com3 = coms[2];
            }

        });
        builder.setPositiveButton("취소", null);
        builder.create().show();

    }
}
