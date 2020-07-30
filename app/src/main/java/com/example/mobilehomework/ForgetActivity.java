package com.example.mobilehomework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ForgetActivity extends AppCompatActivity {

    Button btn_yzm;
    Button next;
    String  str_yzm;
    String str_pn;
    EditText pn;
    ImageView yzm;
    private String realCode;//生成的验证码

    IdentifyCode IdentifyingCode = new IdentifyCode();
    EditText get_yzm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        get_yzm = findViewById(R.id.get_yzm);
        btn_yzm = findViewById(R.id.get_pw);
        next = findViewById(R.id.fg_next);
        yzm = findViewById(R.id.img_yzm);
        pn = findViewById(R.id.get_pn);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_yzm = btn_yzm.getText().toString();
                str_pn = pn.getText().toString();
                if(str_yzm.equals(realCode) && get_yzm.getText().toString() != ""){
                    Toast.makeText(ForgetActivity.this,  "验证码正确,将跳转至下一页面", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ForgetActivity.this,LoginActivity.class);

                    startActivity(i);
                }
            }
        });
        btn_yzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yzm.setImageBitmap(IdentifyingCode.getInstance().createBitmap());
                realCode=IdentifyingCode.getInstance().getCode().toLowerCase();
            }
        });
        yzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str_yzm == realCode){
                    Toast.makeText(ForgetActivity.this,  "验证码正确", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForgetActivity.this,  "验证码错误，请重新输入", Toast.LENGTH_SHORT).show();

                    get_yzm.setText("");
                }
            }
        });
    }
}
