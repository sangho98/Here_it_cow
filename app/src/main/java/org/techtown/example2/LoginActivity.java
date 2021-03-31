package org.techtown.example2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ComponentActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.SmsManager;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InlineSuggestionsRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.techtown.example2.ui.slideshow.SlideshowFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity implements VariableInterface{
    private static final int SMS_SEND_PERMISSON = 1;
    private int MY_PERMISSIONS_REQUEST_LOCATION = 10;
    private static final int ACCESS_FINE_LOCATION_PERMISSON = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }


    public void sendSms() {

    }

    public void clicklogin(View v)
    {
        onLogin();
    }
    public void clickbluetooth(View v)
    {

    }
    public void clickresister(View v)
    {
        Intent intent = new Intent(this, ResisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLogin(){
        EditText id_Edit = (EditText) findViewById(R.id.editTextTextPersonName3);
        EditText pwd_Edit = (EditText) findViewById(R.id.editTextTextPersonName5);

        String ID = new String(id_Edit.getText().toString());
        String PWD = new String(pwd_Edit.getText().toString());

        // 추가 사항 -> id 나 pwd가 공백일시 toast 메시지 출력

        String url = "http://hereitcow.ga/login";
        HashMap<String, String> map = new HashMap<>();

        map.put("ID",ID);
        map.put("password",PWD);
        String result = HttpRequest.postRequest(url,map);

        if(statusCode.get(0) == 555){
            //로그인 실패 status
            Toast toast=Toast.makeText(LoginActivity.this,"로그인 실패!" +"\n"+" 아이디나 패스워드를 확인해주세요.",Toast.LENGTH_SHORT);
            toast.show();
        }else if(statusCode.get(0) == 666){
            //로그인 성공 status
            uid.add(ID);
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();

        }
    }
    public void onPopup(View v)
    {

    }

}