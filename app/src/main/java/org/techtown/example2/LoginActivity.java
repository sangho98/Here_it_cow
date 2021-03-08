package org.techtown.example2;

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
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.SmsManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity implements VariableInterface{
    private static final int SMS_SEND_PERMISSON = 1;
    SmsManager mSMSManager;
    Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSMSManager = SmsManager.getDefault();
        btnSend = (Button) findViewById(R.id.sms);
        //권한이 부여되어 있는지 확인
        int permissioncheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if(permissioncheck  == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(), "SMS 수신권한 있음", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "SMS 수신권한 없음", Toast.LENGTH_SHORT).show();

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)){
                Toast.makeText(getApplicationContext(), "SMS권한이 필요합니다", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.SEND_SMS}, SMS_SEND_PERMISSON);
        }
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }
    public void sendSms() {
        String phoneNo = "01041565974";
        String sms = ((Location)Location.context_main).location;
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, sms, null, null);
            Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "전송 오류!", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();//오류 원인이 찍힌다.
            e.printStackTrace();
        }
    }

    public void clicklogin(View v)
    {
        onLogin();
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

        String url = "http://140.238.26.22/login";
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
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();

        }
    }
    public void onPopup(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.MyCustomDialogStyle);
        builder.setMessage("메세지를 보냅니다.");
        builder.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setTitle("경고");
        builder.setIcon(R.drawable.warming);
        AlertDialog ad = builder.create();
        ad.show();
        //ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(255,62,79,92)));
        ad.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        Button nbotton = ad.getButton(DialogInterface.BUTTON_POSITIVE);
        nbotton.setTextColor(Color.WHITE);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                TimerTask task = new TimerTask() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        ad.dismiss();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task,3000);
            }
        });
        thread.start();
    }

    public void clicklocation(View view){
        Intent intent = new Intent(this, Location.class);
        startActivity(intent);
        finish();
    }

}