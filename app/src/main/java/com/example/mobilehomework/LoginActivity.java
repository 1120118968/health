package com.example.mobilehomework;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText edt_school;
    EditText edt_user;
    EditText edt_login_psd;
    TextView txt_register;
    TextView txt_find_psd;
    Button btn_login;
    String school;
    String user;
    String login_psd;
    String command;
    String Attribute;
    Handler handler;
    ClientThread clientThread;

    private boolean bindService;
    private MyService.Work work;
    private MyService myService;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        edt_school = findViewById(R.id.edt_school);
        edt_user = findViewById(R.id.edt_user);
        edt_login_psd = findViewById(R.id.edt_login_psd);
        txt_find_psd = findViewById(R.id.forget_password);
        txt_register = findViewById(R.id.register);

        btn_login = findViewById(R.id.btn_login);



        handler = new Handler()//①
        {

            @Override
            public void handleMessage(Message msg) {

                // 如果消息来自于子线程
                Intent data = new Intent(LoginActivity.this, UserActivity.class);
                if (msg.what == 0x123) {


                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//
//                    data.putExtra("name",user);//数据从登录页面传至签到页面
//                    data.putExtra("tchid",psd);
//                    data.putExtra("loginpsd",loginpsd);
                    startActivity(data);


                } else if(msg.what == 0x000){
                    Toast.makeText(LoginActivity.this, "密码或用户名错误", Toast.LENGTH_SHORT).show();
                }

            }


        };

        clientThread = new ClientThread(handler);
        new Thread(clientThread).start();//①android studio ctrl+alt+l 规范化文本格式 ECLIPSE CTRL+SHIFT+F
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                school = edt_school.getText().toString();
                user = edt_user.getText().toString();
                login_psd = edt_login_psd.getText().toString();

                command = "登录";


                Message msg = new Message();


                msg.what = 0x345;

                Intent data = new Intent(LoginActivity.this, UserActivity.class);
                startActivity(data);
                msg.obj = user + " " + school + " " + login_psd + " " + command ;
              //  clientThread.revHandler.sendMessage(msg);

            }
        });
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
        txt_find_psd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,ForgetActivity.class);
                startActivity(i);
            }
        });
    }
}
