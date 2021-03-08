package org.techtown.example2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity implements VariableInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
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



}