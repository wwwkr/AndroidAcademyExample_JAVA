package com.rtw181204.game07surfaceview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    Context context;
    SurfaceHolder holder;//공장장 객체 참조변수

    //하청직원객체 참조변수
    GameThread gameThread;

    int width, height; //GameView의 가로세로 사이즈즈

   public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        //Surface(메모리 화면)을 관리하는 공장장객체 얻어오기
        holder = getHolder();
        //공장장에게 비서객체 추가하기
        holder.addCallback(this);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //GameView가 화면에 보여질때 자동 실행

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        //surfaceCreated()메소드가 실행된 후 자동 실행
        //Surface의 사이즈가 결정되는 메소드
        //이때부터 작업 시작

        this.width = getWidth();
        this.height = getHeight();

        //직원객체 생성
        if(gameThread==null){
            gameThread = new GameThread();
            gameThread.start();
        }


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //GameView가 화면에서 가려질때 자동 실행

    }

    //inner class/////////////////////////////////////////
    //게임의 실제 모든 작업을 수행하는 하청직원클래스 설계
    class GameThread extends Thread{

          Bitmap imgDragon;
          int x, y;
          int w, h;

         int dx, dy;
          //초기화 작업 메소드
        void init(){
            Resources res=context.getResources();
            imgDragon = BitmapFactory.decodeResource(res,R.drawable.dragon);

            x= width/2;
            y= height/2;

            w= imgDragon.getWidth()/2;
            h=imgDragon.getHeight()/2;

            dx = 5;
            dy = 5;

        }



        //모든 그려내는 작업메소드
        void drawAll(Canvas canvas){

            canvas.drawBitmap(imgDragon,x-w,y-h,null);
        }
        //모든 움직이는 작업메소드
        void moveAll(){

            x+=dx;
            y+=dy;

            if(x<w || x>width-w){
                dx = -dx;
                x+=dx;
            }

            if(y<h || y>height-h){
                dy = -dy;
                y+=dy;
            }

        }

       @Override
        public void run() {

            //초기화작업 실행
               init();

            //메모리화면(Surface)에 그림을 그리고
            //그려진 도화지(Canvas)를 본사 (MainActivity)에게 전송
            //이 작업을 반복
            Canvas canvas =null;
            while (true) {
                //1. 공장장에게 Surface에 그려지는 Canvas 얻어오기
                canvas = holder.lockCanvas();

                //2. Canvas에 그려내는 작업 (onDraw()같은 역할)
                try{
                    synchronized (holder){
                        //작업들
                        moveAll();

                        drawAll(canvas);
                    }


                }
                finally {
                    //3. 본사(MainActivitiy)에 Canvas전송
                    if(canvas!=null) holder.unlockCanvasAndPost(canvas);
                }


            }
        }//run method...

    }//GameThread class......
    /////////////////////////////////////////////////////

}//GameView class
