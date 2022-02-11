package com.rtw181204.spinmon;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.Random;

public class Enemy {

    int width,height;
    Bitmap img;
    int x, y;
    int w, h;
    boolean isDead=false; //미사일 맞아 죽었을 때
    boolean isOut = false; //화면 밖으로 나갔는가?
    boolean wasShown = false; //화면에 보여진 적이 있는가?


    Bitmap[] imgs;
    int index=0; //이미지 번호(날개짓)

    int kind; //적군 종류

    double radian; //이동각도
    int speed;     //이동속도

    int angle;     //회전각도

    int HP;        //생명력

    int loop=0;

    Rect screenRec; //화면사이즈 사각형 영역객체 참조변수

    //Gauge이미지
    Bitmap imgG;
    Bitmap[] imgGs;

    public Enemy(int width, int height, Bitmap[][] imgEnemy, int playerX, int playerY, Bitmap[][] imgGauge) {

        this.width = width; this.height = height;

        Random rnd = new Random();

        //적군 종류(0:white 1:yellow 2:pink)
        int n = rnd.nextInt(10);//0-9
        kind = (n<5)? 0 : (n<8)? 1: 2;

        //적군의 생명력(white:1 yellow:5 pink:3)
        HP = (kind==0)?1 : (kind==1)?5 : 3;

        //1차원배열 imgs : 1종류의 적군이미지 선택
        imgs = imgEnemy[kind];
        img= imgs[0];
        w= img.getWidth()/2;
        h= img.getHeight()/2;

        //적군이 생성되는 위치 .. 화면밖에 Random하게
        double r = Math.toRadians(rnd.nextInt(360));
        x=(int)(width/2 + Math.cos(r)*width);
        y=(int)(height/2 - Math.sin(r)*width);

        //적군과 플레이어 사이의 각도 계산(적군이 이동할 각도)
        radian = Math.atan2(y-playerY, playerX-x);
        angle = (int)(270 - Math.toDegrees(radian));
        speed = kind==0? w/4 : kind==1? w/7 : w/10;
        if(speed==0) speed=1;

        //화면 사이즈 사각형 객체
        screenRec = new Rect(0,0,width,height);

        //게이지 이미지 선택
        if(kind>0){//white가 아니면 게이지를 가지도록
           imgGs = imgGauge[kind-1];
           imgG = imgGs[0];
        }




    }

    //데미지 받는 메소드
    void damaged(int n){

        HP-=n;
        if(HP<=0){
            isDead=true;
            return;
        }

        //게이지 이미지를 변경
        imgG = imgGs[imgGs.length-HP];


    }


    void move(int playerX, int playerY){

        //적군의 종류가 pink일때 kind==2
        if(kind==2){
            //새로운 각도 계산 및 회전각도 변경
            radian = Math.atan2(y-playerY, playerX-x);
            angle = (int)(270 - Math.toDegrees(radian));
       }

        //날개짓
        loop++;
        if(loop%3==0){
            index++;
            if(index>3)index=0;

            img= imgs[index];
        }


        //이동
        x=(int)(x+Math.cos(radian)*speed);
        y=(int)(y-Math.sin(radian)*speed);


        //적군의 좌표가 화면 안으로 들어왔는가
        if(screenRec.contains(x,y)) wasShown = true;

        //화면에 보여진 적이 있다면..
        if(wasShown){
            //화면밖으로 나가면 제거
            if(x<-w || x>width+w || y<-h || y>height+h){
                isOut=true;
            }
        }


    }
}
