package com.rtw181204.ex69locationgeocoding;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText etAddress, etLat, etLng;

    double latitude, longitute;

    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAddress = findViewById(R.id.et_address);
        etLat = findViewById(R.id.et_lat);
        etLng = findViewById(R.id.et_lng);

        //지오코딩을 하려면 서버에서 데이터를 읽어오므로
        //인터넷 퍼미션이 요구됨

    }




    public void clickBtn(View view) {
        //주소->좌표(지오코딩)
        String addr = etAddress.getText().toString();

        address = addr;
        //지오코딩 작업을 수행하는 객체 생성
        Geocoder geocoder = new Geocoder(this, Locale.KOREA);
        try {
            List<Address> addresses = geocoder.getFromLocationName(addr, 3);

            StringBuffer buffer = new StringBuffer();
            for(Address t : addresses){
                buffer.append(t.getLatitude()+","+t.getLongitude()+"\n");
            }

            //결과 좌표값 중에 0번 위치가 가장 대표 좌표임
            latitude = addresses.get(0).getLatitude();
            longitute = addresses.get(0).getLongitude();

           //다이얼로그 보여주기
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(buffer.toString()).create().show();

        } catch (IOException e) {
            Toast.makeText(this, "검색실패", Toast.LENGTH_SHORT).show();
        }

    }

    public void clickBtn2(View view) {

        //좌표->주소(역지오코딩)
        double lat = Double.parseDouble(etLat.getText().toString());
        double lng = Double.parseDouble(etLng.getText().toString());


        Geocoder geocoder = new Geocoder(this, Locale.KOREA);

        try {
            List<Address> addresses = geocoder.getFromLocation(lat,lng,3);

            StringBuffer buffer = new StringBuffer();
            for(Address t : addresses){
                buffer.append(t.getCountryName()+"\n");
                buffer.append(t.getCountryCode()+"\n");
                buffer.append(t.getPostalCode()+"\n");
                buffer.append(t.getAddressLine(0)+"\n"); // 도로명주소
                buffer.append(t.getAddressLine(1)+"\n"); //빌딩번호(없으면 null)
                buffer.append(t.getAddressLine(2)+"\n"); //호실번호(없으면 null)
                buffer.append("-------------------\n");


                new AlertDialog.Builder(this).setMessage(buffer.toString()).show();
            }
        } catch (IOException e) {
            Toast.makeText(this, "검색실패", Toast.LENGTH_SHORT).show();
        }


    }

    public void clickShowMap(View view) {
        //구글지도앱(or 네이버맵, 카카오맵)을 통해 지도 표시
        //디바이스에 맵앱이 여러개면 선택할 수 있는 다이얼로그가 표시됨
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);

        //지도 좌표 데이터 정보 Uri
        Uri uri = Uri.parse("geo:"+latitude+","+longitute+"?z=16");

        //구글지도는 기본 마커가 없음[카카오맵, 네이버맵은 마커가 기본으로 있음]
        uri = Uri.parse("geo:"+latitude+","+longitute+"?q="+latitude+","+longitute+"("+address+")");

        intent.setData(uri);

        startActivity(intent);


    }
}
