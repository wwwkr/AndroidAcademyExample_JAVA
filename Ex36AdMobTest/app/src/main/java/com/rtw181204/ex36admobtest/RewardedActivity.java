package com.rtw181204.ex36admobtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class RewardedActivity extends AppCompatActivity {

    RewardedVideoAd rewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded);

        //리워디드 광고 객체 생성
        rewardedVideoAd= MobileAds.getRewardedVideoAdInstance(this);

        //광고 리스너 설정
        rewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                //로딩이 완료되면
                rewardedVideoAd.show();

            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {

            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

                //일정 시간의 도영ㅇ상 시청을 완료하여
                //보상 받을 조건을 충족할 떄 자동 실행되는 메소드
                //매개변수(파라미터)가 보상 내역을 가지고 있음

                String type = rewardItem.getType();
                int cnt = rewardItem.getAmount();

                Toast.makeText(RewardedActivity.this, type+" : "+cnt, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {

                Toast.makeText(RewardedActivity.this, "FAIL", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onRewardedVideoCompleted() {

            }
        });

        //광고요청하면서 광고단위ID를 지정
        rewardedVideoAd.loadAd("ca-app-pub-5500023940184327/9504708364",new AdRequest.Builder().build());


    }

    public void clickBtn(View view) {
        //광고요청하면서 광고단위ID를 지정
        rewardedVideoAd.loadAd("ca-app-pub-5500023940184327/9504708364",new AdRequest.Builder().build());
    }
}
