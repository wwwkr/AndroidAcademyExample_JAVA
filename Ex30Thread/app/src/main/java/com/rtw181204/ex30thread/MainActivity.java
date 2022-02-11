package com.rtw181204.ex30thread;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView tv;

    int num=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv= findViewById(R.id.tv);
    }

    public void clickBtn(View view) {
        //오래걸리는 작업(ex. 네트워크작업(필수) or 음악플레이 or DB사용)
        //별도의 Thread를 사용하지 않으므로..Main Thread가 ㅓ리함



        for (int i = 0; i < 20; i++) {


            num++;
            //화면에 num값 출력


                    tv.setText(num+"");



            //0.5초 대기
            try {
                Thread.sleep(500);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //MainThread는 위 작업을 수행하는 약 10초 동안 다른작업을 아예 못함
        //반복하면서 TextView에 num값을 출력하는 작업을 요청하지만
        //Main Thread가 반복문 밖으로 나와서 텍스트를 출력하는 작업을 수행하지 못함
        //clickBtn메소드가 종료된 후에야 출력할 수 있으며 그래서 20이란 숫자가 찍힘



    }//clickBtn

    public void clickBtn2(View view) {
        //오래걸리는 작업을 별로의 Thread객체를 이용해서 실행


        MyThread t = new MyThread();

        t.start();
    }

    //오래걸리는 작업 스레드 클래스 설계

    class MyThread extends Thread{

        @Override
        public void run() {



            for(int i=0; i<20; i++){

                num++;

                //에러 - UI작업은 MainThread만 가능
                //별도의 Thread는 UI변경 작업 불가능
                //tv.setText(num+"");error

                //그래서 UI Thread에게 UI변경작업(setText) 요청
                //Handler객체에게 메세지 전송 요청
                //방법1. Handler객체 이용
                handler.sendEmptyMessage(0);

                //방법2. 액티비티의 메소드 중 runOnUiThread()라는 메소드 이용
                //이 메소드의 매개변수로 UI변경작업에 대한
                //권한을 위임받을 Runnable(Thread)객체 전달
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(num+"");
                    }
                });

                //약 10초 소요되는 작업
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            super.run();
        }


    }//MyThread class

    //MainActivity의 멤버변수
    Handler handler = new Handler(){

        //sendMessage()가 호출되면 자동으로 실행되는 메소드
        @Override
        public void handleMessage(Message msg) {

            //이 메소드 안에서는 UI작업 가능함
            tv.setText(num+"");

            super.handleMessage(msg);
        }
    };



}
