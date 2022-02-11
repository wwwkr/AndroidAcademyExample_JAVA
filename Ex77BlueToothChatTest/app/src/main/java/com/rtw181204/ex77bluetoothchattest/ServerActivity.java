package com.rtw181204.ex77bluetoothchattest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
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

public class ServerActivity extends AppCompatActivity {

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public static final int REQ_ENABLE =10;
    public static final int REQ_DISCOVERY =20;

    BluetoothAdapter bluetoothAdapter;

    BluetoothServerSocket serverSocket;
    BluetoothSocket socket;

    DataInputStream dis;
    DataOutputStream dos;

    ServerThread serverThread;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        tv = findViewById(R.id.tv);

        //블루투스관리자 객체 소환하기
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //블루투스가 없는 디바이스인지
        if(bluetoothAdapter==null){
            Toast.makeText(this, "이 기기에는 블루투스가 없습니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        //블루투스장치가 켜져있는지 확인
        if(bluetoothAdapter.isEnabled()){
            //켜져있다면 서버소켓 연결작업
            createServerSocket();
        }else{

            //사용자에게 블루투스장치를 켜도록 요청
            //블루투스 장치 ON선택할 수 있는 액티비티 보이기

            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQ_ENABLE);

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case REQ_ENABLE:
                if(resultCode==RESULT_CANCELED){
                    //Enable을 거부하였으면 앱 사용못함
                    Toast.makeText(this, "블루투스를 허용하지 않았습니다.\n앱을 종료합니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

            case REQ_DISCOVERY:
                if(resultCode==RESULT_CANCELED){
                    Toast.makeText(this, "블루투스 탐색을 허용하지 않았습니다.\n다른 장치에서 이 장치를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //서버소켓을 생성하는 메소드
    void createServerSocket(){

        //통신은 무조건 별도의 Thread가 해야함
        //스레드가 서버소켓을 생성
        serverThread = new ServerThread();
        serverThread.start();

        //다른 블루투스기기에서 이 디바이스의 블루투스를
        //탐색할 수 있도록 허용해야만 함
        //[허용]선택하는 화면 액티비티를 실행(다이얼로그처럼 화면이 보여짐)
        //기본탐색 허용시간이 120초임
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,300); //300초간 허용
        startActivityForResult(intent,REQ_DISCOVERY);

    }

    ////////////////////////////////////////////
    //서버소켓작업 스레드클래스 정의 : inner class
    class ServerThread extends Thread{

        @Override
        public void run() {
           //서버 소켓 생성
            try {
                serverSocket = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("SERVER",MY_UUID );
                setUIText("서버소켓 생성했습니다.");

                //클라이언트의 접속 대기
                socket = serverSocket.accept();
                setUIText("클라이언트가 접속하였습니다.");

                //접속이 되었다면 통신시작
                //통신시작하려면 스트림필요
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());



            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //TextView에 출력하는 메소드
        void setUIText(final String str){

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv.setText(str);
                }
            });
        }

    }
    /////////////////////////////////
}
