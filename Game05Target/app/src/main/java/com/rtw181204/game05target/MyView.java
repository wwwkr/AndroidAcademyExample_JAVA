package com.rtw181204.game05target;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

public class MyView extends View {

    int width, height;

    Bitmap imgBack;     //배경이미지
    Bitmap imgTarget;   //타겟판이미지
    Bitmap imgHole;     //구멍이미지

    int tx,ty; //타겟판의 중심좌표
    int tw,th; //타겟판이미지의 절반폭, 절반높이

    int score=0; //점수
    int total=0; //토탈점수

    Paint paint = new Paint();

    //타겟판의 사각형 3개 영역객체 참조변수
    Rect[] rects = new Rect[3];

    //구멍객체들 ArrayList
    ArrayList<Hole> holes = new ArrayList<>();

   public MyView(Context context) {
        super(context);


        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();

        //res 창고관리자
        Resources res = context.getResources();

        //back.png -> Bitmap
        imgBack = BitmapFactory.decodeResource(res,R.drawable.back);
        //배경이미지를 화면사이즈만하게 리사이징
        imgBack = Bitmap.createScaledBitmap(imgBack,width,height,true);

        //target.png -> Bitmap
        imgTarget = BitmapFactory.decodeResource(res,R.drawable.target_2);
        //리사이징
        imgTarget = Bitmap.createScaledBitmap(imgTarget,560,560,true);

        imgHole = BitmapFactory.decodeResource(res,R.drawable.hole);

        tx= width/2;
        ty = height/2;

        tw = imgTarget.getWidth()/2;
        th = imgTarget.getHeight()/2;

        paint.setColor(Color.WHITE);
        paint.setTextSize(80);

        //3개의 사각형영역 객체 생성
       for(int i =0 ; i<3 ; i++){

           int left   = tx-80-(100*i);
           int right  = tx+80+(100*i);
           int top    = ty-80-(100*i);
           int bottom = ty+80+(100*i);

           rects[i]= new Rect(left,top,right,bottom);
       }

    }//생성자



    @Override
    protected void onDraw(Canvas canvas) {

        //배경이미지
        canvas.drawBitmap(imgBack,0,0,null);

        //타겟판
        canvas.drawBitmap(imgTarget,tx-tw,ty-th,null);


        for(Hole h : holes){
            canvas.drawBitmap(h.img,h.x-h.w  , h.y-h.h , null);
        }
        //구멍자국들 그리기
        /*for(int i =0 ; i<holes.size();i++){

            Hole h = holes.get(i);

            canvas.drawBitmap(h.img,h.x - h.w , h.y - h.h, null);
        }*/

        //점수 표시하기
        canvas.drawText("점수 : "+score,20,100,paint);
        canvas.drawText("총점 : "+total,tx+20,100,paint);




    }//onDraw Method.........


    //모든 View클래스가 가지고 있는 메소드
    //이 View를 터치하였을 때 자동으로 호출되는 메소드


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //터치이벤트의 액션 알아내기
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:

                //터치한 지점좌표 얻어오기
                int x = (int)event.getX();
                int y = (int)event.getY();


                score = 0;


                int[] trad = new int[]{80,180,280};
                int[] scores = new int[]{10,6,12,4,15,8,10,6,12,4,15,8,10};
                //터치한 지점(x, y)이 어느 영역인지 판별!!
                for(int i =0 ; i<3 ; i++){


                    //원 영역안에 있는지 판별
                    if(Math.pow(x-tx,2)+Math.pow(y-ty,2)<=Math.pow(trad[i],2)){
                        //맞았다
                        Hole hole = new Hole(imgHole,x,y);
                        holes.add(hole);

                        //터치다운한 x, y 지점과 원의 중심좌표 tx, ty
                        //사이에 이루는 각도계산
                        double radian =Math.atan2(ty-y,x-tx);//각도계산식 radian단위!!
                        int angle = (int)Math.toDegrees(radian);//radian을 몇도로;
                        if(angle<0) angle+=360;

                        Log.i("AAA", angle+"");
                       for(int k = 0 ; k <13 ; k++){
                           if(angle<=15+(30*k)){

                               score = scores[k] *(3-i);
                               total += score;
                               break;
                           }
                       }

                        break;
                    }

//
//                    //사각형 영역안에 있는지 판별
//                    if(rects[i].contains(x,y)){
//
//
//                        //타겟에 맞았다는 것을 의미함
//                        //구멍자국 만들기
//                        Hole hole = new Hole(imgHole,x,y);
//                        holes.add(hole);
//
//                        //점수 계산
//                        score = 10 - i*2;
//                        total += score;
//
//
//                        break;
//                    }



                }

                //화면갱신
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                break;

            case MotionEvent.ACTION_MOVE:
                break;
        }


        return true;
    }
}//MyVIew class.......
