package com.rtw181204.spinmon;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class GameActivity extends AppCompatActivity {

    GameView gv;
    TextView tvScore;
    TextView tvCoin;
    TextView tvGem;
    TextView tvBomb;
    TextView tvChampion;

    View dialog = null;

    Animation ani;

    MediaPlayer mp;

    ToggleButton tbMusic, tbSound, tbVibrate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gv = findViewById(R.id.gv);

        tvScore = findViewById(R.id.tv_score);
        tvCoin = findViewById(R.id.tv_coin);
        tvGem = findViewById(R.id.tv_gem);
        tvBomb = findViewById(R.id.tv_bomb);
        tvChampion = findViewById(R.id.tv_champion);

        tbMusic = findViewById(R.id.tb_music);
        tbSound = findViewById(R.id.tb_sound);
        tbVibrate = findViewById(R.id.tb_vibrate);

        tbMusic.setChecked(G.isMusic);
        tbSound.setChecked(G.isSound);
        tbVibrate.setChecked(G.isVibrate);

        //토글버튼 선택상황 변경 리스너 실행
        tbMusic.setOnCheckedChangeListener(checkedChangeListener);
        tbSound.setOnCheckedChangeListener(checkedChangeListener);
        tbVibrate.setOnCheckedChangeListener(checkedChangeListener);


        mp = MediaPlayer.create(this,R.raw.my_friend_dragon);
        mp.setLooping(true);


    }

    CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            switch(buttonView.getId()){
                case R.id.tb_music:
                    G.isMusic = isChecked;
                    if(G.isMusic)mp.setVolume(0.5f,0.5f);
                    else mp.setVolume(0,0);
                    break;

                case R.id.tb_sound:
                    G.isSound = isChecked;
                    break;

                case R.id.tb_vibrate:
                    G.isVibrate =isChecked;
                    break;
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if(G.isMusic) mp.setVolume(0.5f, 0.5f);
        else mp.setVolume(0,0);

        mp.start();
    }

    @Override
    protected void onPause() {

        gv.pauseGame();
        super.onPause();

        if(mp!=null && mp.isPlaying()){
            mp.pause();
        }
    }


    @Override
    protected void onDestroy() {

        if(mp!=null){
            mp.stop();
            mp.release();
            mp=null;
        }

        gv.stopGame(); //GameView 종료
        super.onDestroy();
    }

    public void clickPause(View view) {
        if(dialog!=null)return;
        gv.pauseGame();
        dialog = findViewById(R.id.dialog_pause);

        dialog.setVisibility(View.VISIBLE);

        //애니메이션
        ani = AnimationUtils.loadAnimation(this,R.anim.appear_dialog_pause);
        dialog.startAnimation(ani);
    }

    public void clickShopClass(View view) {

        appearDialog(R.id.dialog_shop);
    }

    public void clickShopItem(View view) {
        appearDialog(R.id.dialog_shop);
    }

    public void clickSetting(View view) {
        appearDialog(R.id.dialog_setting);
    }

    //다이얼로그 보여주는 메소드
    void appearDialog(int id){

        if(dialog!=null) return;
        gv.pauseGame();

        dialog = findViewById(id);
        dialog.setVisibility(View.VISIBLE);

        //애니메이션
        ani = AnimationUtils.loadAnimation(this,R.anim.appear_dialog);
        dialog.startAnimation(ani);



   }




    public void clickQuit(View view) {

        if(dialog!=null) return;
        //게임뷰를 일시정지
        gv.pauseGame();
        //Quit 다이얼로그(View)를 보여주기
        dialog = findViewById(R.id.dialog_quit);
        dialog.setVisibility(View.VISIBLE);

        //뷰 애니메이션으로 위에서 떨어지는 느낌
        ani = AnimationUtils.loadAnimation(this,R.anim.appear_dialog_quit);
        dialog.startAnimation(ani);


    }

    @Override
    public void onBackPressed() {

        if(dialog!=null) return;
        //게임뷰를 일시정지
        gv.pauseGame();
        //Quit 다이얼로그(View)를 보여주기
        dialog = findViewById(R.id.dialog_quit);
        dialog.setVisibility(View.VISIBLE);

        //뷰 애니메이션으로 위에서 떨어지는 느낌
        ani = AnimationUtils.loadAnimation(this,R.anim.appear_dialog_quit);
        dialog.startAnimation(ani);
    }

    //다이얼로그 사라지기 애니메이션 적용
    void disappearDialog(){

        ani = AnimationUtils.loadAnimation(this,R.anim.disappear_dialog);

        ani.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dialog.setVisibility(View.GONE);
                dialog = null;

                gv.resumeGame();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        dialog.startAnimation(ani);
    }

    //다이얼로그 안에 있는 버튼들은 모두 이 메소드
    public void clickBtn(View view) {

        switch (view.getId()){
            case R.id.dialog_setting_check:
                disappearDialog();

                break;
            case R.id.dialog_shop_check:
                disappearDialog();

                break;
            case R.id.dialog_quit_ok:
                finish();
                break;

            case R.id.dialog_quit_cancel:

                dialog.setVisibility(View.GONE);
                dialog=null;
                //게임 이어하기
                gv.resumeGame();
                break;

            case R.id.dialog_pause_resume:
                ani =AnimationUtils.loadAnimation(this,R.anim.disappear_dialog_pause);

                //애니메이션의 상태리스너추가
                ani.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        dialog.setVisibility(View.GONE);
                        dialog=null;
                        //게임 이어하기
                        gv.resumeGame();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                dialog.startAnimation(ani);

                break;
        }
    }


    //GameThread가 sendMessage()를 하면 메세지를 전달하는 객체
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            gv.pauseGame();

            //전달받은 메세지의 데이터 얻어오기
            Bundle data = msg.getData();
            int score = data.getInt("Score",0);
            int coin = data.getInt("Coin",0);

            Intent intent = new Intent(GameActivity.this,GameoverActivity.class);

            //GameoverActivity에 전달할 데이터들
            intent.putExtra("Score",score );
            intent.putExtra("Coin",coin);

            startActivity(intent);

            finish();

        }
    };
}
