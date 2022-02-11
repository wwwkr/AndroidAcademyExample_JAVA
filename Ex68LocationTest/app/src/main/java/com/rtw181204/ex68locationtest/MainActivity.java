package com.rtw181204.ex68locationtest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvProviders;
    TextView tvBestProvider;
    TextView tvMyLocation;
    TextView tvAutolocation;

    LocationManager locationManager;

    boolean isEnter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvProviders = findViewById(R.id.tv_providers);
        tvBestProvider = findViewById(R.id.tv_bestprovider);
        tvMyLocation = findViewById(R.id.tv_mylocation);
        tvAutolocation = findViewById(R.id.tv_autolocation);

        //위치정보를 관리하는 객체 소환하기
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //위치정보제공자
        //1. gps : 가장 높은 정확도, 무료, 실내불가, 베터리소모량이 높음
        //2. network : 중간 정확도, 유료or무료, 어디서나 가능, 베터리소모량 중간
        //3. passive : 다른 앱의 마지막 위치정보를 통해서 위치 얻어오기, 정확도가 가장 낮음, 사용빈도 거의 없음

        //디바이스에서 사용가능한 모든 위치정보 제공자들 확인
        List<String> providers = locationManager.getAllProviders();
        String s = "";

        for (String provider : providers) {
            s += provider + ",";
        }

        tvProviders.setText(s);

        //최고의 위치제공자 판별요청

        //베스트 위치정보제공자 알아내기(반드시 위치정보 사용에 대한 퍼미션 필요)
        Criteria criteria = new Criteria();
        criteria.setCostAllowed(true); //비용지불 감수
        criteria.setAccuracy(Criteria.NO_REQUIREMENT);//정확도를 요하는가?
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);//배터리 소모량
        criteria.setAltitudeRequired(true);//고도에 대한 위치가 필요한가

        String baseProvider = locationManager.getBestProvider(criteria, true);
        tvBestProvider.setText(baseProvider);

        //마시멜로우(api23버전)부터 동적퍼미션 필요 : 앱을 다운로드 받을 때 뿐만 아니라 사용할 때도 퍼미션을 체크하는 방식
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int permissionResult = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            //퍼미션 허가가 되어있지 않다면
            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                //사용자에게 퍼미션을 요청하는 다이얼로그 보여주기
                //커스텀할 수 없는 다이얼로그 즉, 이미 만들어져 있는
                //다이얼로그를 요청해야함

                String[] permissons = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permissons, 10);
            }
        }
    }//onCreate Method

    //requestPermissions()실행으로 보여진 다이얼로그에서
    //퍼미션 [허가/거부]여부를 선택하였을 때 자동으로 호출되는 메소드


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 10:
                //[허가]를 선택했는가
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "위치정보제공에 동의하셨습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "이 앱의 기능 사용이 제한됩니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void clickBtn(View view) {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        //현재 내 위치 얻어오기
        Location location = null; //위도, 경도, 정보를 가지고 있는 객체

        if (locationManager.isProviderEnabled("gps")) {
            location = locationManager.getLastKnownLocation("gps");
        } else if (locationManager.isProviderEnabled("network")) {
            location = locationManager.getLastKnownLocation("network");
        }

        if (location == null) {
            tvMyLocation.setText("내 위치 정보를 찾을 수 없습니다.");
        } else {
            //위도, 경도 얻어오기
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            tvMyLocation.setText(latitude + ", " + longitude);
        }

    }

    public void clickBtn2(View view) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        //내 위치 자동갱신
        if (locationManager.isProviderEnabled("gps")) {


            locationManager.requestLocationUpdates("gps", 5000, 2, locationListener);

        }else if(locationManager.isProviderEnabled("network")){

            locationManager.requestLocationUpdates("network", 5000, 2, locationListener);
        }
    }

    public void clickBtn3(View view) {

        //내 위치 자동갱신 종료
        locationManager.removeUpdates(locationListener);
    }


    //위치정보갱신을 듣는 리스너
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //위치정보 갱신시마다 Location객체 전달
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            tvAutolocation.setText(latitude + ", " + longitude);

            //특정 지점에 들어갔을 때 이벤트 발생하고 싶다면
            //왕십리역에 좌표 : 37.562139, 127.038369

            //내위치 (latitude, longitude)와 왕십리역사이의 실제거리계산
            float[] result = new float[3];//거리계산 결과를 저장할 빈 배열 객체
            Location.distanceBetween(latitude,longitude,37.562139, 127.038369, result);

            //result[0]에 두 좌표 사이의 m(미터)거리가 계산되어 저장됨
            if(result[0]<50){ //50m 이내 인가?

                if(isEnter==false){
                    Toast.makeText(MainActivity.this, "축하합니다. 이벤트 달성!", Toast.LENGTH_SHORT).show();
                    isEnter=true;
                }
                else {

                    isEnter=false;
                }

            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


}
