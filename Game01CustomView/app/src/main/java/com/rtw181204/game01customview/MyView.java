package com.rtw181204.game01customview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class MyView extends View {

    int width; //화면의 가로
    int height; //화면의 세로


    Bitmap[] img = new Bitmap[2]; //그림이미지 객체 참조변수 배열
    int x,y; //이미지의 중심좌표
    int w, h; //이미지의 절반폭, 절반높이

    int dx, dy; //이동각도(부호), 속도

    int index =0; //이미지번호

    int loop=0;
    public MyView(Context context) {
        super(context);

        //Phone의 해상도 정보를 얻어오기
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();


        //rabbit_1.png -> Bitmap 객체로 생성
        Resources res = context.getResources(); //res폴더 창고관리자
        img[0] = BitmapFactory.decodeResource(res,R.drawable.rabbit_1);
        //리사이징된 새로운 Bitmap객체 얻어내기
        img[0] = Bitmap.createScaledBitmap(img[0],300,300,true);


        img[1] = BitmapFactory.decodeResource(res,R.drawable.rabbit_2);
        //리사이징된 새로운 Bitmap객체 얻어내기
        img[1] = Bitmap.createScaledBitmap(img[1],300,300,true);

        //이미지의 좌표값
        x=width/2; //화면의 가운데
        y=height/2; //화면의 가운데


        //이미지의 절반높이, 절반폭
        w=img[0].getWidth()/2;
        h=img[0].getHeight()/2;

        //최초 이동 각도, 속도
        dx= 3;
        dy= 3;

        //행운의 편지 보내기
        handler.sendEmptyMessageDelayed(0,20);

    }//constructor

    void move(){
        x+=dx;
        y+=dy;

        //화면이 벽에 닿았을 때
        //왼쪽벽
        if(x<w){
            dx = -dx;
            x=w;
        }

        //오른쪽벽

        if(x>width-w){
            dx=-dx;
            x=width-w;
        }
        //위쪽벽
        if(y<h){
            dy=-dy;
            y=h;
        }

        //아래쪽벽
        if(y>height-h){
            dy=-dy;
            y=height-h;
        }
    }
    //생성자 메소드가 종료된 후 자동으로 1번 실행되는 메소드
    //이 MyView의 화면을 그려내는 용도의 메소드
    @Override
    protected void onDraw(Canvas canvas) {

        //좌표변경
        move();
        //화면에 표시될 내용을 그려내기
        //화가 객체(Canvas)를 통해서

        //이미지 그려내기
        canvas.drawBitmap(img[index],x-w,y-h,null);

        loop++;
        if(loop%10==0) {
            index = 1 - index;
        }

   }

   //애니메이션이 되려면 그림을 여러장 그려내야함
    //onDraw()메소드 한번에 1장이 그려짐
    //그러므로 여러장을 그리려면 onDraw()를 여러번 호출되도록 해야만 함

    //메세지 전달자 객체에게 행운의 편지 개념을 만들기
    Handler handler = new Handler(){
        //sendEmptyMessage()메소드가 호출되면 실행되는 메소드

        @Override
        public void handleMessage(Message msg) {
            //화면갱신 : 화면을 지우고 onDraw()가 다시 호출됨
            invalidate();
            handler.sendEmptyMessageDelayed(0,20);
        }
    };
}