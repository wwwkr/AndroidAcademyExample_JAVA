package com.rtw181204.ex36admobtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Sample AdMob app ID: ca-app-pub-3940256099942544~334751171
        MobileAds.initialize(this, "ca-app-pub-5500023940184327~2362206202");



        //배너광고객체 참조
        adView= findViewById(R.id.adview);

        //광고요청
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public void clickBtn(View view) {

        Intent intent = new Intent(this,InterstitialActivity.class);
        startActivity(intent);

    }

    public void clickBtn2(View view) {

        Intent intent = new Intent(this,RewardedActivity.class);
        startActivity(intent);


    }
}
