package com.rtw181204.project02;

import android.graphics.Bitmap;

public class Player {

    int width, height; //게임뷰사이즈

    Bitmap img;
    int x, y;
    int w, h;

    double radian = 0;//이동각도
    int speed; //이동속도

    Bitmap[][] imgs;

    int kind;


    public Player(int width, int height, Bitmap[][] imgPlayer, int kind){

        imgs = imgPlayer;

        //플레이어의 종류
        this.kind = kind;
        img = imgs[kind][0];

        w = img.getWidth()/2;
        h = img.getHeight()/2;

        //플레이어의 처음 시작위치
        x = width/2;
        y = height/2;

        //이동속도
        speed = w/4;

    }

    void move(){

    }

}
