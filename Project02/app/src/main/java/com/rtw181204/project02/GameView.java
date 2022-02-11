package com.rtw181204.project02;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    Context context;
    SurfaceHolder holder;

    int width, height; //GameView 사이즈

    GameThread gameThread;




    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context =context;
        holder = getHolder();
        holder.addCallback(this);
    }//생성자

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //게임뷰가 화면에 보여질때 자동 실행되는 메소드
        if(gameThread==null){
            gameThread = new GameThread();
            gameThread.start();
        }else{

        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //surfaceCreated()실행 후 실행되는 메소드

        this.width =getWidth();
        this.height =getHeight();


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    //inner class////////////
    class GameThread extends Thread{

        boolean isRun = true;

        Bitmap imgBack;
        Bitmap[][] imgPlayer = new Bitmap[1][3];
        Bitmap imgJoypad;


        //플레이어 객체참조변수
        Player me;
        int playerKind = 0;


        void init(){ //초기화

            //이미지를 Bitmap객체로
            makeBitmap();

            //플레이어 객체 생성
            me = new Player(width, height, imgPlayer ,playerKind);







        }


        void makeBitmap(){
            Resources res = context.getResources();
            int size = 0;

            imgBack = BitmapFactory.decodeResource(res, R.drawable.back_03);
            imgBack = Bitmap.createScaledBitmap(imgBack,width,height,true);


            size = height/8;

            for(int i=0; i<3; i++){

                imgPlayer[0][i] = BitmapFactory.decodeResource(res, R.drawable.char_b_01+i);
                imgPlayer[0][i] = Bitmap.createScaledBitmap(imgPlayer[0][i],size,size,true);
            }

        }

        void drawAll(Canvas canvas){

            //배경그리기
            canvas.drawBitmap(imgBack,0,0,null);

            //플레이어
            canvas.save();
            canvas.drawBitmap(me.img, me.x-me.w, me.y-me.h, null);
            canvas.restore();
        }

        void makeAll(){

        }

        void moveAll(){

        }

        @Override
        public void run() {

            init();

            Canvas canvas = null;

            while (isRun){
                canvas = holder.lockCanvas();

                try{
                    synchronized (holder){

                        makeAll();

                        drawAll(canvas);
                    }
                }finally {
                    if(canvas!=null) holder.unlockCanvasAndPost(canvas);
                }


            }
        }
    }

    ///////////////////////
}
