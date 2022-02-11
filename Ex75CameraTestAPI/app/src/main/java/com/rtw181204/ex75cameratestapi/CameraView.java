package com.rtw181204.ex75cameratestapi;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder holder;

    //카메라 하드웨어 객체 참조변수
    //minAPI 21버전 이상인 앱으로 개발할때는 CameraDevice(camera2)사용해도 됨
    Camera camera;

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //이때부터 미리보기화면(surface)를 만들 수 있음
        //카메라 하드웨어의 셔터열기
        camera = Camera.open(0); //0:back 1:front

        //카메라가 open되면 취득한 정보를 지속적으로 전달해줌
        //이를 미리보기로 surface에 보여줘야함
        try {
            //미리보기 화면설정
            camera.setPreviewDisplay(holder);
            //카메라는 무조건 가로방향이 기준
            //세로로 보고싶다면 90도 회전
            camera.setDisplayOrientation(90);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //미리보기 시작
        camera.startPreview();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        //미리보기 종료
        camera.stopPreview();

        //카메라 닫기
        camera.release();
        camera = null;

    }
}
