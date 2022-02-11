package com.rtw181204.ex31thread2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {


    Mythread thread;
    Boolean isRun = true;
    Boolean isWait = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //액티비티가 메모리에서 종료될 때 자동으로 실행되는 메소드


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Thread의 run()메소드가 종료되면 Thread는 끝남
        if(thread!=null)thread.stopThread();
    }

    //화면에 보이지 않을때 자동으로 실행되는 메소드
    @Override
    protected void onPause() {
        super.onPause();

        //스레드 일시정지
        if(thread!=null)thread.pauseThread();

    }

    //화면에 보일때 자동으로 실행되는 메소드
    @Override
    protected void onResume() {
        super.onResume();
        if(thread!=null)thread.resumeThread();

    }

    public void clickBtn(View view) {
        //5초마다 현재시간 Toast로 출력해주기
        thread = new Mythread();
        thread.start();
    }

    //이너클래스
    class Mythread extends Thread{

        @Override
        public void run() {

            while (isRun){

                //현재시간 출력
                Date date = new Date();//이 객체는 현재날짜와 시간을 가지고 있음
                final String now = date.toString();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(MainActivity.this,now,Toast.LENGTH_SHORT).show();
                    }
                });

                //5초동안대기
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                //일시정지
                synchronized (this) {
                    if (isWait) {

                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }



            }//while



        }//run

        void stopThread(){
            isRun=false;
            synchronized (this) {
                this.notify();
            }
        }

        void pauseThread(){
            isWait=true;
        }

        void resumeThread(){
            isWait=false;

            synchronized (this) {
                this.notify();
            }
        }

    }//Mythread

}
