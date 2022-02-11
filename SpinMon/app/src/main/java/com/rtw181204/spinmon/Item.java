package com.rtw181204.spinmon;

import android.graphics.Bitmap;

import java.util.Random;

public class Item {

    int width, height;

    Bitmap img;
    int x, y;
    int w, h;

    boolean isDead = false;

    int dx, dy;

    int kind; //아이템종류

    int life = 500;

    public Item(int width, int height, Bitmap[] imgItem, int ex, int ey) {

        this.width = width; this.height = height;
        x = ex; y = ey;

        //종류
        //0:coin 1:gem 2:fast 3:protect 4:magnet 5:bomb 6:strong
        Random rnd = new Random();
        int n = rnd.nextInt(100); //0-99
        kind = n<65?0: n<66?1: n<81?2: n<84?3: n<96?4: n<97?5: 6;

        img = imgItem[kind];
        w = img.getWidth()/2;
        h = img.getHeight()/2;

        int k = rnd.nextBoolean()?1: -1;
        dx = w/6*k;

        k = rnd.nextBoolean()?1: -1;
        dy = h/6*k;

    }

    void move(int playerX, int playerY){

        //플레이어와 아이템사이의 각도계산
        double radian = Math.atan2(y-playerY,playerX-x);

        //계산된 각도로 이동
        x=(int)(x+Math.cos(radian)*w);
        y=(int)(y-Math.sin(radian)*w);

    }

    void move(){

        x += dx;
        y += dy;

        if(x<=w){
            dx = -dx;
            x = w;
        }

        if(x>=width-w){
            dx = -dx;
            x = width-w;
        }

        if(y<=h){
            dy = -dy;
            y = h;
        }

        if(y>=height-h){
            dy = -dy;
            y = height-h;
        }

        life--;
        if(life<=0) isDead=true;
    }
}
