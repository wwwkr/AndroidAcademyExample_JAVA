package com.rtw181204.ex77bluetoothchattest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class BTDeviceListActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<String> deviceList = new ArrayList<>();
    ArrayAdapter adapter ;

    BluetoothAdapter bluetoothAdapter;
    BTDiscoveryReceiver btDiscoveryReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btdevice_list);

        lv = findViewById(R.id.lv);
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, deviceList);
        lv.setAdapter(adapter);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //기존에 페어링되어 있는 장치가 있는지
        Set<BluetoothDevice> deviceSet = bluetoothAdapter.getBondedDevices();
        //얻어온 장치를 리스트에 추가
        for(BluetoothDevice devices : deviceSet){
            String name = devices.getName();
            String macAddress = devices.getAddress();

            deviceList.add(name+"\n"+macAddress);
        }

        //탐색시작전에 탐색결과방송을 수신하는 리시버를 등록
        btDiscoveryReceiver = new BTDiscoveryReceiver();
        IntentFilter intentFilter = null;

        intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(btDiscoveryReceiver,intentFilter);

        intentFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(btDiscoveryReceiver,intentFilter);


        //주변 장치 탐색 시작
        bluetoothAdapter.startDiscovery();



        //리스트뷰의 아이템 선택 리스너 추가

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              String s =   deviceList.get(position);
              //주소만 분리
                String[] strs = s.split("\n");

                //[1]번 배열 요소의 결과를 ClientActivity에 전달
                Intent intent = getIntent();
                intent.putExtra("macAddress",strs[1]);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }//onCreate()

    @Override
    protected void onPause() {
        super.onPause();

        bluetoothAdapter.cancelDiscovery();

        unregisterReceiver(btDiscoveryReceiver);
    }

    class BTDiscoveryReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){

                Toast.makeText(context, "디바이스 탐색 종료", Toast.LENGTH_SHORT).show();
            }else if(action.equals(BluetoothDevice.ACTION_FOUND)){

                //장치를 찾았으므로 그 장치에 대한 정보를 얻어오기
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                String name = device.getName();
                String macAddress = device.getAddress();

                deviceList.add(name+"\n"+macAddress);
                adapter.notifyDataSetChanged();


            }

        }
    }
}
