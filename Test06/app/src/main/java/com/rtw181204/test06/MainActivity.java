package com.rtw181204.test06;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int o=0;
    int m = 0;
    int s= 0;
    TextView tv;
    MyThread thread = new MyThread();

    Boolean isTrue=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv= findViewById(R.id.tv);

    }

    public void clickStart(View view) {


        if(thread==null){
            thread=new MyThread();
        }
        thread.start();
        if(isTrue==false){

            isTrue=true;
        }



    }


    public void clickStop(View view) {



        isTrue=false;

        o=0;
        m=0;
        s=0;

        thread=null;



    }

    public void clickPause(View view) {


        isTrue=false;
    }

    class MyThread extends Thread{

        @Override
        public void run() {

            while (isTrue){



                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(thread!=null) {
                    s++;
                }
                final String str = String.format("%02d:%02d:%02d",o,m,s);






                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        tv.setText(str);


                    }
                });
                if(s==99){
                    m++;

                    s=0;

                }



                if(m==60){
                    m=0;
                    o++;
                }

            }
        }
    }


}
