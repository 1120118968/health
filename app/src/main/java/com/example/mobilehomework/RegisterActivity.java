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
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText edt_user;
    EditText edt_name;
    EditText edt_psd;
    EditText edt_email;
    EditText edt_enter_psd;
    Button enterRegister;
    String  user;//用户名
    String name;//姓名
    String psd;//密码
    String email;//邮箱
    String enterPsd;//确认密码
    Handler handler;

    ClientThread clientThread;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edt_email = findViewById(R.id.edt_email);
        edt_enter_psd = findViewById(R.id.edt_enter_psd);
        edt_name = findViewById(R.id.edt_name);
        edt_psd = findViewById(R.id.edt_psd);
        edt_user = findViewById(R.id.edt_user);
        enterRegister = findViewById(R.id.enter_register);




        handler = new Handler()//①
        {

            @Override
            public void handleMessage(Message msg) {

                // 如果消息来自于子线程
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                if (msg.what == 0x1234) {

                    Toast.makeText(RegisterActivity.this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                    startActivity(i);//start LoginActivity

                } else if(msg.what == 0x000){
                    Toast.makeText(RegisterActivity.this, "注册失败,密码或用户名错误", Toast.LENGTH_SHORT).show();
                }

            }


        };

        clientThread = new ClientThread(handler);
// 客户端启动ClientThread线程创建网络连接、读取来自服务器的数据
        new Thread(clientThread).start();//①
        enterRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edt_email.getText().toString();
                enterPsd = edt_enter_psd.getText().toString();
                psd = edt_psd.getText().toString();
                user = edt_user.getText().toString();
                name = edt_name.getText().toString();
                if (psd.equals(enterPsd)){
                    String command = "注册";


                    Message msg = new Message();

                    msg.what = 0x345;
                    msg.obj = user + " " + name + " " + psd + " " + command ;

                    clientThread.revHandler.sendMessage(msg);

                }
            }
        });

    }
}
