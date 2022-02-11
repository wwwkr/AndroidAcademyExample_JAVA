package com.rtw181204.game04analogclock;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class MyView extends View {

    int width, height; //화면사이즈

    Bitmap imgDial; //시계 이미지 객체
    Bitmap[] imgPins =new Bitmap[3]; //핀 이미지

    int cx, cy; //시계그림의 중심좌표
    int cw, ch; //시계이미지의 절반폭, 절반높이

    int pw; //핀 이미지의 절반폭
    int ph; //핀 이미지의 전체높이

    int rHour, rMin, rSec; // 시분초의 회전각도
    int hour, min, sec; //현재 시분초

    Paint paint = new Paint();

   public MyView(Context context) {
        super(context);


        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();


        //dial.png -> Bitmap객체로 생성
        Resources res = getResources();
        imgDial = BitmapFactory.decodeResource(res,R.drawable.dial);

        //pin1, 2, 3.png -> Bitmap객체로 생성
        imgPins[0] = BitmapFactory.decodeResource(res,R.drawable.pin_1);
        imgPins[1] = BitmapFactory.decodeResource(res,R.drawable.pin_2);
        imgPins[2] = BitmapFactory.decodeResource(res,R.drawable.pin_3);

        //시계 중심좌표
        cx = width/2;
        cy = height/2;

        //시계이미지 절반폭, 절반높이
        cw = imgDial.getWidth()/2;
        ch = imgDial.getHeight()/2;

        //핀이미지의 절반폭, 전체높이
        pw = imgPins[0].getWidth()/2;
        ph = imgPins[0].getHeight()-10;


        //디지털시간의 글씨색상, 크기지정
       paint.setColor(Color.RED);
       paint.setTextSize(80);

        //행운의 편지를 처음 발송
         handler.sendEmptyMessageDelayed(0,500);


    }//생성자

    //핀의 회전각도 계산 .. (현재시간에 맞추어)하는 메소드
    void calAngle(){

       //현재시간 알아내기
        GregorianCalendar calendar = new GregorianCalendar();
        hour = calendar.get(Calendar.HOUR);
        min = calendar.get(Calendar.MINUTE);
        sec = calendar.get(Calendar.SECOND);

        //회전각도 계산
        rSec = sec *6;
        rMin = min *6 + rSec/60;
        rHour =hour*30 + rMin/12;




    }

    @Override
    protected void onDraw(Canvas canvas) {

       ///핀의 회전각도 계산 (현재시간에 맞추어)
        calAngle();

        //시계배경 이미지
        canvas.drawBitmap(imgDial,cx-cw,cy-ch,null);

        //시침
        canvas.save();
        canvas.rotate(rHour,cx,cy);
        canvas.drawBitmap(imgPins[2],cx-pw,cy-ph,null);
        canvas.restore();

        //분침
        canvas.save();
        canvas.rotate(rMin,cx,cy);
        canvas.drawBitmap(imgPins[1],cx-pw,cy-ph,null);
        canvas.restore();


        //초침
        canvas.save();
        canvas.rotate(rSec,cx,cy);
        canvas.drawBitmap(imgPins[0],cx-pw,cy-ph,null);
        canvas.restore();


        //디지털시간 글씨 출력
        String date = String.format("%02d:%02d:%02d",hour,min,sec);
        canvas.drawText(date,cx-cw,cy-ch,paint);

    }

    //행운의편지 전달자 객체

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            //화면갱신 : 화면을 지우고 onDraw()메소드가 다시 호출
            invalidate();

            handler.sendEmptyMessageDelayed(0,500);

        }
    };
}
