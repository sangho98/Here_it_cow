package org.techtown.example2;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.UUID;

public class BluetoothService {
    private static final int REQUEST_ENABLE_BT =2;
    private static final String TAG="BluetoothService";
    private static final UUID MY_UUID =UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothAdapter btAdapter;
    private Activity mActivity;
    private Handler mHandler;
    public BluetoothService(Activity activity,Handler handler){
        mActivity = activity;
        mHandler = handler;

        //bluetoothAdapter 얻기
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.d(TAG,"테스트");
    }
    public boolean getDeviceState()
    {
        Log.d(TAG,"Check the Bluetooth support");
        if(btAdapter==null)
        {
            Log.d(TAG, "Bluetooth is not available");
            return false;
        }
        else
        {
            Log.d(TAG, "Bluetooth is available");
            return true;
        }
    }
    /*(2) enableBluetooth() : bluetooth활성화 메소드 (getDeviceState가 true를 반환시 활성화를 요청)*/
    public void enableBluetooth()
    {
        Log.i(TAG, "Check the enable Bluetooth");
        if(!btAdapter.isEnabled())
        {
            //기기의 블루투스 상태가 off일 경우
            Log.d(TAG, "Bluetooth Enable Request");

            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mActivity.startActivityForResult(i, REQUEST_ENABLE_BT);
        }
        else
        {
            //기기의 블루투스 상태가 On일 경우..
            Log.d(TAG, "Bluetooth Enable Now");
        }

    }

    public void startDiscovery(){

        Toast.makeText(mActivity,"starts scanning...",Toast.LENGTH_SHORT).show();
        btAdapter.startDiscovery();
    }

}

