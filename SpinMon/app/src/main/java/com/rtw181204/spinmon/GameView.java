package com.rtw181204.spinmon;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    Context context;
    SurfaceHolder holder;

    int width, height;//GameView의 사이즈

    GameThread gameThread;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context= context;
        holder= getHolder();
        holder.addCallback(this);

    }//constructor..(생성자)

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //GameView가 화면에 보여질때 자동 실행되는 메소드
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //surfaceCreated()실행 후 자동 실행되는 메소드
        //이때 서피스의 크기가 결정됨

        this.width= getWidth();
        this.height= getHeight();

        //이곳에서 게임이 시작됨
        if( gameThread==null ){//직원이 없다..그말은 처음 시작!
            gameThread= new GameThread();
            gameThread.start();


        }else{
            //분명.. 게임은 일시정지가 되어있었을 것임.
            //게임 이어하기(resume)처리를 해야만 함.
            gameThread.resumeThread();
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //GaemView가 화면에서 안보이면 자동 실행

    }

    //게임종료 기능메소드
    void stopGame(){
        gameThread.stopThread();
    }

    //게임일시정지 기능 메소드
    void pauseGame(){
        gameThread.pauseThread();
    }

    //게임이어하기 기능 메소드
    void resumeGame(){
        gameThread.resumeThread();
    }

    //터치이벤트 처리
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action= event.getAction();
        int x= (int)event.getX();
        int y= (int)event.getY();

        switch ( action ){
            case MotionEvent.ACTION_DOWN:
                gameThread.touchDown(x, y);
                break;

            case MotionEvent.ACTION_UP:
                gameThread.touchUp(x, y);
                break;

            case MotionEvent.ACTION_MOVE:
                gameThread.touchMove(x, y);
                break;
        }

        return true;
    }

    /////////////////////////////////////////////////
    //inner class
    class GameThread extends Thread{

        boolean isRun= true;
        boolean isWait= false;

        Bitmap imgBack;
        Bitmap[] imgMissile= new Bitmap[3];
        Bitmap[][] imgPlayer= new Bitmap[3][4];
        Bitmap[][] imgEnemy= new Bitmap[3][4];
        Bitmap[][] imgGauge= new Bitmap[2][];
        Bitmap imgJoypad;
        Bitmap[] imgDust= new Bitmap[6];
        Bitmap[] imgItem= new Bitmap[7];
        Bitmap imgProtect; //보호막
        Bitmap imgStrong; //강화미사일
        Bitmap imgBtnBomb; //폭탄버튼 이미지

        boolean isBomb= false; //폭탄버튼을 눌렀는가?
        Rect recBomb;//폭탄버튼이미지의 사각형 영역 좌표관리 객체

        int protectRad;//보호막 이미지의 반지름
        int protectAngle=0;//보호막 이미지 회전각도

        int jpx, jpy; //조이패드의 중심좌표
        int jpr;      //조이패드이미지의 반지름
        boolean isJoypad= false;  //조이패드를 눌렀는가?

        //이미지의 투병도를 조절하기 위한 페인트객체
        Paint alphaPaint= new Paint();

        int backPos=0;//배경이미지의 좌측좌표

        //플레이어 객체참조변수
        Player me;
        int playerKind=0; //플레이어의 종류[ 0:red   1:purple   2:black ]

        Random rnd= new Random();

        ArrayList<Missile> missiles= new ArrayList<>();
        ArrayList<Enemy> enemies= new ArrayList<>();
        ArrayList<Dust> dusts= new ArrayList<>();
        ArrayList<Item> items= new ArrayList<>();

        //미사일 발사간격
        int missileGap=3;

        int level= 1; //게임난이도 : 1~10

        //아이템 적용시간관련 변수들
        int fastItem=0;
        int protectItem=0;
        int magnetItem=0;
        int strongItem=0;

        int score=0; //점수
        int coin=0;  //코인개수
        int bomb=3;//폭탄개수


        SoundPool sp;
        int sdChdie, sdFire, sdCoin, sdGem;
        int sdProtect, sdItem, sdMondie;

        //진동관리자
        Vibrator vibrator;


        //초기화 메소드
        void init(){

            //진동관리자 객체를 운영체제로 부터 소환
            vibrator= (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

            //사운드들을 사운드풀에 등록하기!!
            sp= new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            sdChdie= sp.load(context, R.raw.ch_die, 1);
            sdFire= sp.load(context, R.raw.fireball, 1);
            sdCoin= sp.load(context, R.raw.get_coin, 1);
            sdGem= sp.load(context, R.raw.get_gem, 1);
            sdProtect= sp.load(context, R.raw.get_invincible, 1);
            sdItem= sp.load(context, R.raw.get_item, 1);
            sdMondie= sp.load(context, R.raw.mon_die, 1);





            //png->Bitmap객체로..
            makeBitmaps();

            //플레이어 객체 생성
            me= new Player(width, height, imgPlayer, playerKind);

            //조이패드의 좌표값
            jpx= jpr;
            jpy= height-jpr;

            //텍스트뷰에 초기값들 설정!!
            setTextUI();
        }

        void setTextUI(){

            // Activity클래스의 runOnUiThread()메소드와 같은 역할
            // View클래스의 멤버메소드임.
            post(new Runnable() {
                @Override
                public void run() {
                    //이 안에서는 UI변경 가능함.

                    GameActivity ga= (GameActivity)context;

                    String s=null;

                    s= String.format("%07d", score);
                    ga.tvScore.setText(s);

                    s= String.format("%04d", coin);
                    ga.tvCoin.setText(s);

                    s= String.format("%04d", G.gem);
                    ga.tvGem.setText(s);

                    s= String.format("%04d", bomb);
                    ga.tvBomb.setText(s);

                    s= String.format("%07d", G.champion);
                    ga.tvChampion.setText(s);

                }
            });

        }


        //그림파일을 -> Bitmap객체로 만들어주는 메소드
        void makeBitmaps(){
            Resources res= context.getResources();
            int size=0;

            size= height/5;
            imgBtnBomb= BitmapFactory.decodeResource(res, R.drawable.btn_bomb);
            imgBtnBomb= Bitmap.createScaledBitmap(imgBtnBomb, size, size, true);
            //버튼이미지의 사각형 영역좌표객체
            recBomb= new Rect(width-size, height-size, width, height);

            size= height/10;
            imgStrong= BitmapFactory.decodeResource(res, R.drawable.bullet_04);
            imgStrong= Bitmap.createScaledBitmap(imgStrong, size, size, true);

            size= height/4;
            imgProtect= BitmapFactory.decodeResource(res, R.drawable.effect_protect);
            imgProtect= Bitmap.createScaledBitmap(imgProtect, size, size, true);
            protectRad= imgProtect.getWidth()/2;


            size= height/16;
            for(int i=0; i<imgItem.length; i++){
                imgItem[i]= BitmapFactory.decodeResource(res, R.drawable.item_0_coin+i);
                imgItem[i]= Bitmap.createScaledBitmap(imgItem[i], size, size, true);
            }

            //Dust 이미지
            Bitmap img= BitmapFactory.decodeResource(res, R.drawable.dust);
            float[] ratio= new float[]{1.0f, 1.4f, 1.7f, 0.7f, 0.4f, 2.0f};
            for(int i=0; i<6; i++){
                size= (int)(height/9 * ratio[i]);
                imgDust[i]= Bitmap.createScaledBitmap(img, size, size, true);
            }


            //조이패드 이미지
            imgJoypad= BitmapFactory.decodeResource(res, R.drawable.img_joypad);
            imgJoypad= Bitmap.createScaledBitmap(imgJoypad, height/2, height/2, true);
            jpr= imgJoypad.getWidth()/2;


            //Gauge 이미지
            imgGauge[0]= new Bitmap[5];
            for(int i=0; i<imgGauge[0].length; i++){
                imgGauge[0][i]= BitmapFactory.decodeResource(res, R.drawable.gauge_step5_01+i);
                imgGauge[0][i]= Bitmap.createScaledBitmap(imgGauge[0][i], height/9, height/36, true);
            }

            imgGauge[1]= new Bitmap[3];
            for(int i=0; i<imgGauge[1].length; i++){
                imgGauge[1][i]= BitmapFactory.decodeResource(res, R.drawable.gauge_step3_01+i);
                imgGauge[1][i]= Bitmap.createScaledBitmap(imgGauge[1][i], height/9, height/36, true);
            }


            //미사일 이미지
            size= height/10;
            for(int i=0; i<imgMissile.length; i++){
                imgMissile[i]= BitmapFactory.decodeResource(res, R.drawable.bullet_01+i);
                imgMissile[i]= Bitmap.createScaledBitmap(imgMissile[i], size, size, true);
            }


            //배경이미지
            int n= rnd.nextInt(6); //0~5
            imgBack= BitmapFactory.decodeResource(res, R.drawable.back_01+n);
            imgBack= Bitmap.createScaledBitmap(imgBack, width, height, true);

            //플레이어 이미지
            size= height/8;
            for(int i=0; i<3; i++){
                imgPlayer[0][i]= BitmapFactory.decodeResource(res, R.drawable.char_a_01+i);
                imgPlayer[0][i]= Bitmap.createScaledBitmap(imgPlayer[0][i], size, size, true);
            }
            imgPlayer[0][3]= imgPlayer[0][1];

            for(int i=0; i<3; i++){
                imgPlayer[1][i]= BitmapFactory.decodeResource(res, R.drawable.char_b_01+i);
                imgPlayer[1][i]= Bitmap.createScaledBitmap(imgPlayer[1][i], size, size, true);
            }
            imgPlayer[1][3]= imgPlayer[1][1];

            for(int i=0; i<3; i++){
                imgPlayer[2][i]= BitmapFactory.decodeResource(res, R.drawable.char_c_01+i);
                imgPlayer[2][i]= Bitmap.createScaledBitmap(imgPlayer[2][i], size, size, true);
            }
            imgPlayer[2][3]= imgPlayer[2][1];

            //적군이미지
            size= height/9;
            for(int i=0; i<imgEnemy.length; i++){
                for(int k=0; k<3; k++){
                    imgEnemy[i][k]= BitmapFactory.decodeResource(res, R.drawable.enemy_a_01+k+(i*3));
                    imgEnemy[i][k]= Bitmap.createScaledBitmap(imgEnemy[i][k], size, size, true);
                }
                imgEnemy[i][3]= imgEnemy[i][1];
            }

        }


        //모든 자동으로 만들어내는 작업 메소드
        void makeAll(){
            //미사일 생성
            if(fastItem>0){//아이템 적용중
                if(G.isSound) sp.play(sdFire, 0.2f, 0.2f, 0, 0, 1);
                missiles.add( new Missile(width, height, imgMissile, me.kind, me.x, me.y, me.angle) );
            }else{//아이템 적용 중 아님
                missileGap--;
                if(missileGap==0){
                    if(G.isSound) sp.play(sdFire, 0.2f, 0.2f, 0, 0, 1);
                    missiles.add( new Missile(width, height, imgMissile, me.kind, me.x, me.y, me.angle) );
                    missileGap=3;
                }
            }


            //적군들 생성
            int p= rnd.nextInt(11-level);
            if(p==0) enemies.add(new Enemy(width, height, imgEnemy, me.x, me.y, imgGauge));


        }

        //모든 움직이는 작업 메소드
        void moveAll(){

            //아이템 움직이기
            for(int i=items.size()-1; i>=0; i--){
                Item t= items.get(i);

                if(magnetItem>0 && t.kind<2) t.move(me.x, me.y);
                else t.move();

                if(t.isDead) items.remove(i);
            }


            //먼지들 움직이기
            for(int i=dusts.size()-1; i>=0; i--){
                Dust t= dusts.get(i);
                t.move();
                if(t.isDead) dusts.remove(i);
            }

            //적군들 움직이기
            for(int i=enemies.size()-1; i>=0; i--){
                Enemy t= enemies.get(i);

                t.move(me.x, me.y);
                if(t.isOut) enemies.remove(i);
                else if(t.isDead){
                    //폭발 효과음
                    if(G.isSound) sp.play(sdMondie, 1,1, 1, 0, 1);

                    //폭발 이미지(먼지이미지)
                    dusts.add( new Dust(imgDust, t.x, t.y) );
                    //아이템 생성
                    items.add( new Item(width, height, imgItem, t.x, t.y) );
                    //점수 획득
                    score += 10*(t.kind+1);
                    setTextUI();

                    enemies.remove(i);
                }
            }


            //미사일 움직이기
            for(int i=missiles.size()-1; i>=0; i--){
                Missile t= missiles.get(i);

                t.move();
                if(t.isDead) missiles.remove(i);
            }


            //플레이어 움직이기
            me.move();

            //배경 움직이기
            backPos--;
            if(backPos<=-width) backPos+=width;

            calItemTime();//아이템 지속시간 계산메소드 호출
        }

        //아이템 지속시간 계산 메소드
        void calItemTime(){

            if(fastItem>0){
                fastItem--;
                if(fastItem==0) me.da=9;
            }

            if(protectItem>0) protectItem--;
            if(magnetItem>0) magnetItem--;
            if(strongItem>0) strongItem--;


        }


        //아이템에 따른 동작!!
        void doItemAction(int kind){
            //아이템 종류에 따라 동작수행!!
            switch (kind){
                case 0://coin
                    if(G.isSound) sp.play(sdCoin, 1,1, 2, 0, 1);
                    coin++;
                    setTextUI();
                    break;
                case 1://gem
                    if(G.isSound) sp.play(sdGem, 1,1, 3, 0, 1);
                    G.gem++;
                    setTextUI();
                    break;
                case 2://fast
                    if(G.isSound) sp.play(sdItem, 1,1, 2, 0, 1);
                    //플레이어가 빨리 회전
                    me.da=15;//한번에 9도씩 회전!!

                    fastItem=200;
                    break;
                case 3://protect
                    if(G.isSound) sp.play(sdProtect, 1,1, 3, 0, 1);
                    protectItem=200;
                    break;
                case 4://magnet
                    if(G.isSound) sp.play(sdItem, 1,1, 2, 0, 1);
                    magnetItem=200;
                    break;
                case 5://bomb
                    if(G.isSound) sp.play(sdItem, 1,1, 2, 0, 1);
                    bomb++;
                    setTextUI();
                    break;
                case 6://strong
                    if(G.isSound) sp.play(sdItem, 1,1, 2, 0, 1);
                    strongItem=200;
                    break;
            }

        }


        //모든 충돌체크 계산 메소드
        void checkCollision(){

            //플레이이와 적군의 충돌
            for(Enemy t : enemies){

                if(protectItem>0){//보호막 상황..
                    if( Math.pow(t.x- me.x, 2)+Math.pow(t.y-me.y, 2) <= Math.pow(t.w+protectRad, 2)){
                        t.isDead=true;//적군만 제거
                    }

                }else{ //보호막이 없는 상황
                    if( Math.pow(t.x- me.x, 2)+Math.pow(t.y-me.y, 2) <= Math.pow(t.w+me.w, 2)){
                        //적군이 닿았다!!
                        //플레이어 터지는 효과음
                        if(G.isSound) sp.play(sdChdie, 1,1, 4, 0, 1);

                        //진동
                        if(G.isVibrate) vibrator.vibrate(500);


                        //적군제거
                        t.isDead=true;

                        me.HP--;
                        if(me.HP<=0){
                            //GameOver 상황
                            //GameoverActivity전환!!
                            //별로 Thread가 직접 Activity를 전화하는 방식은
                            //권장하지 않음.
                            //Handler를 이용하여 MainThread에게 액티비티 전환요청
                            Message msg= new Message();

                            Bundle data= new Bundle();
                            data.putInt("Score", score);
                            data.putInt("Coin", coin);

                            msg.setData(data);

                            ((GameActivity)context).handler.sendMessage(msg);

                        }
                    }

                }


            }


            //플레이어와 아이템의 충돌(아이템 취득)
            for(Item t: items){
                if(Math.pow(t.x-me.x, 2)+Math.pow(t.y-me.y, 2) <= Math.pow(t.w+me.w, 2)){
                    //아이템 취득했음!!
                    doItemAction(t.kind);//아이템종류 전달

                    t.isDead=true; //아이템 제거대상으로 설정
                    break;
                }
            }



            //미사일과 적군의 충돌
            for(Missile t : missiles){
                for(Enemy et :enemies){
                    if( Math.pow(t.x-et.x, 2) + Math.pow(t.y-et.y, 2) <= Math.pow(t.rad+et.w, 2) ){
                        //맞았다!!!
                        et.damaged(t.kind+1);

                        //점수획득
                        score+=5;
                        setTextUI();

                        //미사일 제거
                        if(strongItem==0) t.isDead=true;

                    }

                }
            }

        }

        //모든 그림을 그리는 작업메소드
        void drawAll(Canvas canvas){

            //배경 그리기
            canvas.drawBitmap(imgBack, backPos, 0, null);
            canvas.drawBitmap(imgBack, backPos+width, 0, null);

            //적군들 그리기
            for(Enemy t : enemies){
                canvas.save();
                canvas.rotate(t.angle, t.x, t.y);
                canvas.drawBitmap(t.img, t.x-t.w, t.y-t.h, null);
                canvas.restore();

                //게이지그리기
                if(t.kind>0){
                    canvas.drawBitmap(t.imgG, t.x-t.w, t.y+t.h, null);
                }
            }


            //미사일들 그리기
            for(Missile t : missiles){
                canvas.save();
                canvas.rotate(t.angle, t.x, t.y);
                canvas.drawBitmap((strongItem>0)? imgStrong:t.img, t.x-t.rad, t.y-t.rad, null);
                canvas.restore();
            }

            //아이템들 그리기
            for(Item t : items){
                canvas.drawBitmap(t.img, t.x-t.w, t.y-t.h, null);
            }

            //플레이어 그리기
            canvas.save();
            canvas.rotate(me.angle, me.x, me.y);
            canvas.drawBitmap(me.img, me.x-me.w, me.y-me.h, null);
            canvas.restore();

            //보호막 이미지 그리기
            if(protectItem>0){
                protectAngle+=30;
                canvas.save();
                canvas.rotate(protectAngle, me.x, me.y);
                canvas.drawBitmap(imgProtect, me.x-protectRad, me.y-protectRad, null);
                canvas.restore();
            }



            //Dust 그리기
            for( Dust t : dusts ){
                for(int i=0; i<t.img.length; i++){
                    canvas.drawBitmap(t.img[i], t.x[i]-t.rad[i], t.y[i]-t.rad[i], null);
                }
            }

            //조이패드 그리기
            alphaPaint.setAlpha(isJoypad?240:120); //0~255
            canvas.drawBitmap(imgJoypad, jpx-jpr, jpy-jpr, alphaPaint);

            //폭탄버튼 그리기
            alphaPaint.setAlpha(isBomb?240:120);
            canvas.drawBitmap(imgBtnBomb, recBomb.left, recBomb.top, alphaPaint);

        }


        //GameView가 받은 터치액션을 전달받는 메소드들//
        void touchDown(int x, int y){
            //터치다운한 지점에 조이패드가 있는가?
            if(Math.pow(x-jpx, 2)+ Math.pow(y-jpy, 2) <= Math.pow(jpr, 2) ){
                isJoypad=true;
                //조이패드의 중심좌표와 플레이어 좌표사이의 각도 계산
                //이 각도가 플레이어의 이동각도가 됨.
                me.radian= Math.atan2(jpy-y, x-jpx);
                //플레이어가 움직일 수 있도록!!
                me.canMove=true;
            }


            //터치다운한 지점에 폭탄버튼이 있는가?
            if(recBomb.contains(x, y)){
                isBomb=true;

                if(bomb>0){
                    bomb--;//폭탄개수 감소
                    setTextUI();

                    //적군들 모두 죽어!!!
                    for(Enemy t : enemies){
                        //화면에 보여진 적인 있는 적군만..죽어!!
                        if(t.wasShown) t.isDead=true;
                    }
                }

            }

        }

        void touchUp(int x, int y){
            isJoypad=false;
            me.canMove=false;

            isBomb=false;
        }

        void touchMove(int x, int y){

            if(isJoypad){
                //지속적으로 각도 계산
                me.radian= Math.atan2(jpy-y, x-jpx);
            }

//            if(Math.pow(x-jpx, 2)+ Math.pow(y-jpy, 2) <= Math.pow(jpr, 2) ){
//                isJoypad=true;
//            }else{
//                isJoypad=false;
//            }

        }
        /////////////////////////////////////////



        @Override
        public void run() {

            //초기화 작업
            init();

            Canvas canvas= null;
            while (isRun){
                //1. Surface에 붙어있는 Canvas얻어오기
                canvas= holder.lockCanvas();
                //2. Canvas에 원하는 작업 수행
                try{

                    synchronized (holder){
                        //모든 작업...(마치..onDraw()같은 역할)
                        makeAll();
                        moveAll();
                        checkCollision();

                        drawAll(canvas);
                    }

                }finally {
                    //3. Canvas를 본사(GameActivity)에 전송
                    if(canvas!=null) holder.unlockCanvasAndPost(canvas);
                }

                synchronized (this){
                    if(isWait){
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }//while..

        }//run method..


        //스레드를 종료시키는 메소드
        void stopThread(){
            isRun=false;

            //혹시 일시정지가 되어있다면 깨워라!!!
            synchronized (this){
                this.notify();
            }
        }

        //스레드를 일시정지하는 메소드
        void pauseThread(){
            isWait= true;
        }

        //스레드를 이어하기하는 메소드
        void resumeThread(){
            isWait= false;
            synchronized (this){
                this.notify();
            }
        }




    }//GameThread class...
    /////////////////////////////////////////////////


}//GameView class..
