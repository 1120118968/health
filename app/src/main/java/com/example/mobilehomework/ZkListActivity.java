package com.example.mobilehomework;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ZkListActivity extends AppCompatActivity {

    List<Food> foodList = new ArrayList<>();
    private MyDataBaseHelper dbHelper;
    String selectSql = "select * from food";
    SQLiteDatabase db;
    Button selectFood;
    Button zklist;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zk_list);

        selectFood = findViewById(R.id.select_zk);

        zklist = findViewById(R.id.yt2_list);

        MyDataBaseHelper dbHelper = new MyDataBaseHelper(ZkListActivity.this, "jibu_db",null,1);
        db = dbHelper.getWritableDatabase();
        selectFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = db.query("zk", null, null, null,
                        null, null, null);
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String price = cursor.getString(cursor.getColumnIndex("price"));
                    String kcal = cursor.getString(cursor.getColumnIndex("kcal"));
                    Food p=new Food( name, price, kcal);
                    foodList.add(p);
                }
                LinearLayout ll=(LinearLayout) findViewById(R.id.ll2);
                //把数据显示到屏幕
                for(Food f:foodList)
                {
                    //1.集合中每有一条数据，就new一个TextView
                    TextView tv=new TextView(ZkListActivity.this);
                    //2.把人物的信息设置为文本的内容
                    tv.setText(f.toString());
                    tv.setTextSize(18);
                    //3.把TextView设置成线性布局的子节点
                    ll.addView(tv);
                }
            }


        });

        zklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ZkListActivity.this,YTListActivity.class);
                startActivity(i);
            }
        });
        
    }
}
