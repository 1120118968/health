package com.example.mobilehomework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        imageView = (ImageView) findViewById(R.id.welcome);
        Thread myThread = new Thread() {
            public void run() {
                try

                {
                    //启动登录页面
                    Thread.sleep(3000);
                    Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (
                        InterruptedException e)

                {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
