package com.rtw181204.test15;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    Context context;
    SurfaceHolder holder;

    int width, height ; //GameView의 사이즈

    GameThread gameThread;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);this.context = context;
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        this.width = getWidth();
        this.height = getHeight();

        //게임시작
        if(gameThread==null){
            gameThread = new GameThread();
            gameThread.start();
        }else{

            //이어하기
        }


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    //inner class////////////////

    class GameThread extends  Thread{

        Bitmap imgBack;

        Random rnd = new Random();

        boolean isRun = true;

        void init(){//초기화작업

            makeBitmaps();
        }

        void makeBitmaps(){//Bitmap객체생성

            Resources res = context.getResources();

            //배경이미지
            int n = rnd.nextInt(6);
            imgBack = BitmapFactory.decodeResource(res,R.drawable.back_01+n);
            imgBack = Bitmap.createScaledBitmap(imgBack,width,height,true);
        }

        void drawAll(Canvas canvas){

            canvas.drawBitmap(imgBack,0,0,null);
        }

        @Override
        public void run() {

            init();

            Canvas canvas = null;

            while (isRun){

                canvas = holder.lockCanvas();

                try{
                    synchronized (holder){
                     //모든작업 마치 onDraw

                        drawAll(canvas);
                    }
                }finally {

                    if(canvas!=null) holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
    /////////////////////////////
}
