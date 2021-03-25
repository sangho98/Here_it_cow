package org.techtown.example2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BluetoothScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button scanButton = findViewById(R.id.scanButton);
        setContentView(R.layout.activity_bluetooth_scan);
    }
    public void scanningBtn(View v){

    }
}