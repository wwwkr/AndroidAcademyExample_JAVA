package com.rtw181204.ex84kakaologintest;

import android.app.Application;
import android.content.Context;

import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;

public class GlobalApplication extends Application {

    //본인 참조변수값의 정적 변수
    static GlobalApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        if(instance==null)instance=this;

        //카카오SDK와 앱의 Application객체 연결!!초기화작업
        KakaoSDK.init(new KakaoSDKAdapter());

    }

    //카카오SDK를 Application과 연결해주는 클래스 설계
    private static class KakaoSDKAdapter extends KakaoAdapter {

        @Override
        public IApplicationConfig getApplicationConfig() {

            IApplicationConfig applicationConfig = new IApplicationConfig() {
                @Override
                public Context getApplicationContext() {
                    return instance;
                }
            };


            return applicationConfig;
        }
    }
}
