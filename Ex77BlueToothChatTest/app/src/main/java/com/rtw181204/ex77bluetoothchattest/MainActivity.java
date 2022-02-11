package com.rtw181204.ex77bluetoothchattest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},50);
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 50:
                if(grantResults[0]==PackageManager.PERMISSION_DENIED){

                    Toast.makeText(this, "주변장치 검색이 제한됩니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void clickServer(View view) {

        Intent intent = new Intent(this, ServerActivity.class);
        startActivity(intent);
    }

    public void clickClient(View view) {

       startActivity(new Intent(this, ClientActivity.class));
    }
}
