package com.rtw181204.game06bubbletouch;

import android.graphics.Bitmap;

import java.util.Random;

public class Bubble {

    int width, height; //화면사이즈즈

   Bitmap img;
    int x, y;
    int rad; //그림이미지의 반지름(절반폭, 절반높이)

    boolean isDead=false;

    int dx, dy; //이동방향, 속도

    int cnt=0; //벽 충돌횟수

    public Bubble(int width, int height, Bitmap[] imgBubble, int x , int y) {

        this.width = width;
        this.height = height;

        Random random = new Random();
        int n = random.nextInt(6);//0~5
        img = imgBubble[n];

        this.x = x;
        this.y = y;

        //랜덤한 사이즈
        //반지름
        rad = random.nextInt(61)+20;
        //반지름 사이즈를 기반으로 이미지를 리사이징
        img = Bitmap.createScaledBitmap(img,rad*2,rad*2,true);

        //랜덤한 방향과 속도
        //이동속도 2~10
        int k = random.nextBoolean()?1:-1;
        dx = (random.nextInt(9)+2)*k; //2-10

         k = random.nextBoolean()?1:-1;
        dy = (random.nextInt(9)+2)*k; //2-10

    }


    //움직이는 기능 메소드
    void move(){
        x+=dx;
        y+=dy;

        if(x<=rad || x>= width-rad){
            dx = -dx;
            x += dx;
            cnt++;
        }

        if(y<=rad || y>= height-rad){
            dy= -dy;
            y += dy;
            cnt++;
        }

        //벽 충돌횟수가 3번이상이면 Dead
        if(cnt>=3){
            isDead=true;
        }

    }
}
