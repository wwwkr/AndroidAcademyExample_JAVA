package com.mrhi2018.ex67scenetransition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    RelativeLayout rootLayout;

    Scene scene1;
    Scene scene2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //장명(Scene)을 담을 ViewGroup
        rootLayout= findViewById(R.id.layout_root);

        //2개의 장면(Scene)[레이아웃파일] 객체 생성
        scene1= Scene.getSceneForLayout(rootLayout, R.layout.scene1, this);
        scene2= Scene.getSceneForLayout(rootLayout, R.layout.scene2, this);

        //일단 처음 보여질 장면(Scene)을 화면에 보이도록..
        scene1.enter();
    }

    public void clickBtn1(View v){
        //.enter()는 장면은 바뀌지만 전환효과는 없음.

        //전환효과를 주기위해 전환효과 객체를 만들기
        Transition transition= TransitionInflater.from(this).inflateTransition(R.transition.tran);
        TransitionManager.go(scene1, transition);


    }

    public void clickBtn2(View v){
        Transition transition= TransitionInflater.from(this).inflateTransition(R.transition.tran);
        TransitionManager.go(scene2, transition);
    }

    public void clickBtn3(View v){

    }

}














