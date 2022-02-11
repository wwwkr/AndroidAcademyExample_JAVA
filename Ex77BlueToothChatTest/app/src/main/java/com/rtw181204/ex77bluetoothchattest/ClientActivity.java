package com.rtw181204.ex77bluetoothchattest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ClientActivity extends AppCompatActivity {

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    TextView tv;

    BluetoothAdapter bluetoothAdapter;

    BluetoothSocket socket;

    DataInputStream dis;
    DataOutputStream dos;

    ClientThread clientThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        tv = findViewById(R.id.tv);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter==null){
            Toast.makeText(this, "이 디바이스에는 블루투스가 없습니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if(bluetoothAdapter.isEnabled()){
            //주변 블루투스 장치 탐색하기
            discoveryBluetoothDevices();

        }else{
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,10);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if(resultCode==RESULT_CANCELED){
                    Toast.makeText(this, "종료", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                   discoveryBluetoothDevices();
                }
                break;

            case 100: //디바이스 탐색 액티비티에서 돌아온 결과
                if(resultCode==RESULT_OK){
                    String macAddress = data.getStringExtra("macAddress");


                    //얻어온 주소를 통해 소켓연결 시작
                    clientThread = new ClientThread(macAddress);

                    clientThread.start();
                }

                break;
        }
    }
    //주변에 있는 블루투스장치를 탐색하는 메소드
    void discoveryBluetoothDevices(){

        //블루투스 장치를 탐색하고 그 결과를 리스트뷰로 보여주는
        //액티비티를 실행하고 그 리스트뷰에서 선택한 디바이스의 주소를
        //결과로 받아와야함
        Intent intent = new Intent(this, BTDeviceListActivity.class);

        startActivityForResult(intent,100);

    }





    //서버와 연결하는 Thread 클래스 설계///
    class ClientThread extends Thread{


        String macAddress;

        public ClientThread(String macAddress) {

            this.macAddress = macAddress;

        }

        @Override
        public void run() {
            //mac 주소에 해당하는 블루투스 장치를 얻어오기
            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(macAddress);

            //찾아온 디바이스 서버와 연결된 소켓 얻어오기
            try {
                socket = device.createRfcommSocketToServiceRecord(MY_UUID);
                socket.connect();

                setUIText("서버에 접속하였습니다.");

                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());



            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        void setUIText(final String msg){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv.setText(msg);
                }
            });
        }
    }
    ////////////////////////////////////
}
