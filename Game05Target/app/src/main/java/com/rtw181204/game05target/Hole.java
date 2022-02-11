package com.rtw181204.game05target;

import android.graphics.Bitmap;

public class Hole {

    Bitmap img;
    int x ,y;
    int w, h;

    public Hole(Bitmap img, int x, int y) {
        this.img = img;
        this.x = x;
        this.y = y;


        w= img.getWidth()/2;
        h = img.getHeight()/2;
    }
}
