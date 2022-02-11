package com.rtw181204.test_ex68locationtest;

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
    TextView tvAutoLocation;

    LocationManager locationManager;

    boolean isEnter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvProviders = findViewById(R.id.tv_providers);
        tvBestProvider = findViewById(R.id.tv_bestprovider);
        tvMyLocation = findViewById(R.id.tv_mylocation);
        tvAutoLocation = findViewById(R.id.tv_autolocation);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getAllProviders();

        String s = "";

        for (String provider : providers) {
            s += provider + " , ";
        }

        tvProviders.setText(s);


        Criteria criteria = new Criteria();
        criteria.setCostAllowed(true);
        criteria.setAccuracy(Criteria.NO_REQUIREMENT);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        criteria.setAltitudeRequired(true);

        String baseProvider = locationManager.getBestProvider(criteria, true);
        tvBestProvider.setText(baseProvider);

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

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode) {

            case 10:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "위치정보제공에 동의하셨습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "이 앱의 기능 사용이 제한됩니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void clickBtn(View view) {

        Location location = null;

        if (locationManager.isProviderEnabled("gps")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            location = locationManager.getLastKnownLocation("gps");
        } else if (locationManager.isProviderEnabled("network")) {
            location = locationManager.getLastKnownLocation("network");
        }

        if (location == null) {
            tvMyLocation.setText("내 위치 정보를 찾을 수 없습니다.");
        } else {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();


            tvMyLocation.setText(latitude + " , " + longitude);
        }


    }

    public void clickBtn2(View view) {

        if (locationManager.isProviderEnabled("gps")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates("gps", 5000, 3, locationListener);
        }
    }

    public void clickBtn3(View view) {

        locationManager.removeUpdates(locationListener);
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            tvAutoLocation.setText(latitude+" , "+ longitude);

            float[] result = new float[3];
            Location.distanceBetween(latitude,longitude,37.562139, 127.038369 , result);

            if(result[0]<50){

                if(isEnter==false){
                    Toast.makeText(MainActivity.this, "축하합니다. 이벤트 달성", Toast.LENGTH_SHORT).show();
                }else{
                    isEnter =false;
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
