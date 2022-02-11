package com.rtw181204.spinmon;

import android.graphics.Bitmap;

public class Player {

    int width, height; //GameView화면 사이즈

    Bitmap img; //플레이어의 이미지
    int x, y;   //플레이어의 중심좌표
    int w, h;   //플레이어 이미지의 절반폭, 절반높이

    double radian=0;//이동 각도
    int speed;      //이동 속도
    boolean canMove=false; //움직일 수 있는가? //조이패드를 눌러야 움직임

    int angle=0; //이미지의 회전각도

    //int da=3; //회전각도 변화량 원본
    int da=3; //회전각도 변화량

    Bitmap[][] imgs;//원본이미지들
    int index=0; //이미지번호(날개짓)

    int kind; //플레이어의 종류

    int loop=0;

    int HP = 3;//생명력

   public Player(int width, int height, Bitmap[][] imgPlayer , int kind) {
       this.width = width; this.height = height;
       imgs=imgPlayer;

       //플레이어의 종류[0:RED, 1:PURPLE, 2:BLACK]
       this.kind = kind;

       img = imgs[kind][0];
       w= img.getWidth()/2;
       h= img.getHeight()/2;

       //플레이어의 처음 시작위치
       x = width/2;
       y = height/2;

       //이동속도
        //원본 speed = w/4;
       speed = w/4;

    }

    //날개짓과 움직이는 기능 메소드
    void move(){

       //회전
        angle+=da;
        if(angle>=360) angle-=360;

       //날개짓(이미지변경)
        loop++;
        if(loop%3==0){
            index++;
            if(index>3) index=0;
            img = imgs[kind][index];
        }


        //움직이는 기능
        if(canMove){
            x=(int)(x+Math.cos(radian)*speed);
            y=(int)(y-Math.sin(radian)*speed);

            if(x<w) x=w;
            if(x>width-w) x=width-w;
            if(y<h) y=h;
            if(y>height-h) y=height-h;
        }


    }
}
