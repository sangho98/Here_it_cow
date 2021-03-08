package org.techtown.example2;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class BluetoothActivity extends AppCompatActivity {
    //debugging
    private static final String TAG = "MAIN";

    //layout : 에 관련된 객체 정의
    private Button btn_Connect;

    private BluetoothService bluetoothService_obj = null; //BluetoothService클래스에 접근하기 위한 객체이다.



    private final Handler mHandler = new Handler() {
        //핸들러의 기능을 수행할 클래스(handleMessage)
        public void handleMessage(Message msg) {
        //BluetoothService로부터 메시지(msg)를 받는다.
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate");
        setContentView(R.layout.activity_bluetooth);

        btn_Connect=(Button)findViewById(R.id.connect_btn);
//        btn_Connect.setOnClickListener(mClickListener);
        if(bluetoothService_obj == null){
            bluetoothService_obj = new BluetoothService(this,mHandler);
        }
        if(bluetoothService_obj.getDeviceState()){
            bluetoothService_obj.enableBluetooth();
        }
        btn_Connect.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothService_obj.startDiscovery();
            }
        });
    }


}
