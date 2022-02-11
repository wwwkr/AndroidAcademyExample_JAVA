package com.rtw181204.ex70locationfusedapi;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    //적절한 위치정보제공자(gps, newwork, passive)를 알아서 선정하여 위치정보를 제공

    GoogleApiClient googleApiClient; //위치정보 관리 객체(LocationManger 역할)
    FusedLocationProviderClient providerClient; //위치정보제공자 객체

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);


        //위치정보이므로 퍼미션 작업 필요(동적퍼미션 필요)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int permissionResult = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);

            if (permissionResult == PackageManager.PERMISSION_DENIED) {

                String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permissions, 10);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode) {

            case 10:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "위치정보 사용에 동의하셨습니다.", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(this, "위치정보 사용을 거부하셨습니다.\n사용자의 위치탐색 기능이 제한됩니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void clickBtn(View view) {

        //위치정보관리 객체 생성하기 위한 Builder생성
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        //구글키 사용설정
        builder.addApi(LocationServices.API);
        //connect()했을 때 연결 성공 리스너 설정
        builder.addConnectionCallbacks(connectionCallbacks);

        //connect()했을 때 연결실패 리스너
        builder.addOnConnectionFailedListener(failedListener);


        //위치정ㅈ보 관리 객체 생성(LocationManager 역할)
        googleApiClient = builder.build();

        //준비가 되었으니 위치정보 취득을 위한 연결시도
        googleApiClient.connect();//역할부여

        //위치정ㅂ조제공자 객체 얻어오기
        providerClient = LocationServices.getFusedLocationProviderClient(this);


    }

    //위치정보를 얻기 위한 접근에 성공하였을 때 반응하는 리스너
    GoogleApiClient.ConnectionCallbacks connectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(@Nullable Bundle bundle) {

            Toast.makeText(MainActivity.this, "위치정보 탐색을 시작합니다.", Toast.LENGTH_SHORT).show();

            //위치정보 제공자 객체를 통해서 위치정보를 얻어오도록
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //높은 정확도 : gps 우선
            locationRequest.setInterval(5000); //5초마다 위치정보 탐색

            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            providerClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }

        @Override
        public void onConnectionSuspended(int i) {

        }
    };

    //위치정보 업데이트때마다 반응하는 리스너
    LocationCallback locationCallback = new LocationCallback(){

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Location location = locationResult.getLastLocation();
            tv.setText("현재위치 : "+location.getLatitude()+" , "+location.getLongitude());

        }
    };


    //위치정보를 얻기 위한 접근에 실패하였을 때 반응하는 리스너
    GoogleApiClient.OnConnectionFailedListener failedListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

            Toast.makeText(MainActivity.this, "위치정보 탐색을 시작할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();

        //화면에 안보이면 위치정보 더이상 얻어오지 않도록
        if(providerClient!=null && locationCallback!=null){
            providerClient.removeLocationUpdates(locationCallback);
        }

    }
}
