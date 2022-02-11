package com.rtw181204.ex74cameravideotest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {


    VideoView vv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vv= findViewById(R.id.vv);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int permissionResult = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if(permissionResult==PackageManager.PERMISSION_DENIED){
                String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

                requestPermissions(permissions,100);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 100:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "비디오 촬영가능", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "비디오 촬영불가", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    public void clickBtn(View view) {

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        startActivityForResult(intent,10);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){
                    Uri uri = data.getData();
                    vv.setVideoURI(uri);

                    MediaController controller = new MediaController(this);
                    controller.setAnchorView(vv);

                    vv.setMediaController(controller);


                    //동영상 로딩에 시간이 걸리므로 준비가 완료될 때 실행하도록


                    vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            vv.start();
                        }
                    });
                }

                break;
        }
    }
}
