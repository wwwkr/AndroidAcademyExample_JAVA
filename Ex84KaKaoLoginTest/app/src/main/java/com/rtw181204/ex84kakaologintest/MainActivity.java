package com.rtw181204.ex84kakaologintest;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static com.kakao.util.helper.Utility.getPackageInfo;

public class MainActivity extends AppCompatActivity {

    LoginButton loginButton;

    TextView tvNick;
    TextView tvEmail;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.btn_login);
        tvNick = findViewById(R.id.tv_nick);
        tvEmail = findViewById(R.id.tv_email);
        iv = findViewById(R.id.iv);

        //키해시 문자열 얻어오기
//        String keyHash = getKeyHash(this);
//        Log.i("Tag", keyHash);

        //카카오 로그인버튼을 클릭하여 로그인화면(웹화면)에서
        //로그인 하였을 때 그 결과를 받기위한 Callback객체 생성 및 추가
        //서버와 연결을 담당하는 세션객체에게 콜백 추가

        Session.getCurrentSession().addCallback(sessionCallback);
    }

    //로그인결과를 받기위한 세션이 열렸을 때 Callback
    ISessionCallback sessionCallback = new ISessionCallback() {
        @Override
        public void onSessionOpened() {
            //연결을 성공적으로 열었을때
            Toast.makeText(MainActivity.this, "세션오픈성공", Toast.LENGTH_SHORT).show();
            //로그인이 되었으니 정보 얻어오기
            requestMe();

        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            //실패했을 때
            Toast.makeText(MainActivity.this, "연결실패", Toast.LENGTH_SHORT).show();
       }
    };


    //내 프로필정보 내놔 메소드
    void requestMe(){
        List<String> keys = new ArrayList<>();
        keys.add("properties.nickname");
        keys.add("properties.profile_image");
        keys.add("kakao_account.email");


        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onSuccess(MeV2Response result) {

                String name = result.getNickname();
                String profileImg = result.getProfileImagePath();
                String email = result.getKakaoAccount().getEmail();


                //화면에 보여주기
                tvNick.setText(name);
                tvEmail.setText(email);
                Glide.with(MainActivity.this).load(profileImg).into(iv);

            }
        });
    }




































    public static String getKeyHash(final Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.w("TAG", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
        return null;
    }

    public void clickLogout(View view) {

        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                Toast.makeText(MainActivity.this, "로그아웃", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
