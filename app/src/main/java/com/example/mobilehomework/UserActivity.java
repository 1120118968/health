package com.example.mobilehomework;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.zkk.view.rulerview.RulerView;

public class UserActivity extends AppCompatActivity {

    //用户信息页面
    private RulerView ruler_height;   //身高的view
    private RulerView ruler_weight ;  //体重的view
    private TextView tv_register_info_height_value,tv_register_info_weight_value;
    private Button next;
    CheckBox cb_sex;
    EditText edtname;
    SQLiteDatabase db;

    int height;
    String name;
    int weight;
    String sex = "male";
    int age;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ruler_height=(RulerView)findViewById(R.id.ruler_height);
        ruler_weight=(RulerView)findViewById(R.id.ruler_weight);

        cb_sex = findViewById(R.id.btn_register_info_sex);
        edtname = findViewById(R.id.name);
        name = edtname.getText().toString();

        tv_register_info_height_value=(TextView) findViewById(R.id.tv_register_info_height_value);
        tv_register_info_weight_value=(TextView) findViewById(R.id.tv_register_info_weight_value);
        next = findViewById(R.id.next);

        cb_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;

            }
        });
        if (flag = false){
            sex = "male";
        }else {
            sex = "female";
        }

        ruler_height.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tv_register_info_height_value.setText(value+"");

                height = (int)value;
            }
        });


        ruler_weight.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tv_register_info_weight_value.setText(value+"");
                weight = (int)value;
            }
        });
//默认值
        ruler_height.setValue(165, 80, 250, 1);

        ruler_weight.setValue(55, 20, 200, 0.1f);

        MyDataBaseHelper dbHelper = new MyDataBaseHelper(UserActivity.this, "jibu_db",null,1);
        db = dbHelper.getWritableDatabase();
            //创建数据库

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String usersql = "create table user(name varchar(20),age int(20),sex varchar(20),height int(20),weight int(20) )";
                //信息写入 并 跳转页面
                ContentValues values = new ContentValues();

                values.put("name",name);
                values.put("age",age);
                values.put("sex",sex);
                values.put("height",height);
                values.put("weight",weight);

                db.insert("user", null, values);
                Intent i = new Intent(UserActivity.this,PageSwitcher.class);
                i.putExtra("name",name);
                startActivity(i);
            }
        });

    }
}
