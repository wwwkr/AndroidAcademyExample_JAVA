package com.rtw181204.game02touchevent;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class MyView extends View {

    int width, height; //화면 사이즈

    Bitmap img; //이미지객체
    int rx,ry;    //이미지의 중심좌표
    int rw,rh;    //이미지의 절반폭, 절반높이

    //터치의 이전지점좌표
    int x1,y1;


    public MyView(Context context) {
        super(context);

        //화면사이즈(폰의 해상도) 얻어내기
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();

        width = display.getWidth();
        height = display.getHeight();

        //rabbit_1.png로 Bitmap객체 생성
        Resources res = getResources();
        img = BitmapFactory.decodeResource(res,R.drawable.rabbit_1);
        //이미지 리사이징
        img = Bitmap.createScaledBitmap(img,300,300,true);

        //화면의 가운데 위치를 이미지의 시작 좌표로
        rx=width/2;
        ry=height/2;

        rw=img.getWidth()/2; //이미지의 절반폭
        rh=img.getHeight()/2; //이미지의 절반높이

        //행운의 편지 전송(20ms마다 화면갱신을 위해)
        handler.sendEmptyMessageDelayed(0,20);
    }


    //이 뷰의 화면을 그려내는 메소드 처음 1번 자동실행
    @Override
    protected void onDraw(Canvas canvas) {

        //화가객체(Canvas)를 통해 그려내는 작업코딩
        canvas.drawBitmap(img,rx-rw,ry-rh,null);






    }

    //MyView를 터치하면 자동으로 호출되는 메소드
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //파라미터로 전달된 터치이벤트정보 객체에게 필요한 정보를 얻어내기
        int action =  event.getAction();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                x1 = (int)event.getX();
                y1 = (int)event.getY();
                break;

            case MotionEvent.ACTION_UP:
                break;

            case MotionEvent.ACTION_MOVE:
                int x2 = (int)event.getX();
                int y2 = (int)event.getY();

                //좌표의 변화량 계산
                int dx = x2- x1;
                int dy = y2 -y1;

                //토끼이미지의 좌표에 변화량반영

                rx += dx;
                ry += dy;

                //화면밖으로 안나가게
                if(rx<rw) rx=rw;
                if(rx>width-rw) rx= width-rw;
                if(ry<rh) ry=rh;
                if(ry>height-rh) ry =height-rh;

                //현재좌표가 다음번째의 이전좌표로 되게끔
                x1= x2;
                y1 =y2;
                break;

        }
        //MotionEvent를 없애기 위해 return을 true로
        return true;//메세지를 소비시키는 역할
    }

    //20ms마다 화면을 갱신하기 위해 메신저객체를 이용
    Handler handler = new Handler(){
        //sendEmptyMessage()를 호출하고 20ms가 지나면 자동실행
        @Override
        public void handleMessage(Message msg) {
            invalidate(); //화면갱신(화면을 지우고 onDraw()다시 호출)

            //또다시 20ms후에 편지 발송
            handler.sendEmptyMessageDelayed(0,20);
        }
    };
}
