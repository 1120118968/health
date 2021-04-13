package com.example.mobilehomework;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    Button yt;
    Button zk;
    Button yt2;
    Button mjl;
    Button m;
    Button sport;
    private List<String> datas;
    private AutoCompleteTextView mAuto;
    private MyDataBaseHelper mHelper;
    private SQLiteDatabase mDB;
    private ArrayAdapter<String> adapter;
    String input_string = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        mHelper = new MyDataBaseHelper(this,"jibu_db",null,1);
        mDB = mHelper.getReadableDatabase();
        mAuto = findViewById(R.id.mAuto);
//        datas = getData();
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,datas);
//        mAuto.setAdapter(adapter);
        sport = findViewById(R.id.sport);
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FoodActivity.this,MainActivity.class);
                startActivity(i);
            }
        });


        yt = findViewById(R.id.yt);
        zk = findViewById(R.id.zk);
        yt2 = findViewById(R.id.yt2);
        mjl = findViewById(R.id.mjl);
        m  = findViewById(R.id.m);

        yt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FoodActivity.this,FoodListActivity.class);
                startActivity(i);
            }
        });
        zk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FoodActivity.this,ZkListActivity.class);
                startActivity(i);
            }
        });
        yt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FoodActivity.this,YTListActivity.class);
                startActivity(i);
            }
        });
        mjl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FoodActivity.this,MJLListActivity.class);
                startActivity(i);
            }
        });
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FoodActivity.this,MListActivity.class);
                startActivity(i);
            }
        });

    }
    public List<String>getData(){
        List<String> contents = new ArrayList<String>();
        Cursor result = mDB.rawQuery("select * from yt where name  like ?", new String[]{input_string});
        while(result.moveToNext()){
            contents.add(result.getString(result.getColumnIndex("name")));
          //  contents.add(result.getString(result.getColumnIndex("kcal")));
        }
        return contents;
    }
    public void search(View view){
        String input = mAuto.getText().toString();
        if("".equals(input.trim())){

            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("警告提示");
            builder.setMessage("请输入搜索关键字");
            builder.create().show();
        }else{
                    input = input + "";
                    input_string = "%"+input+"%";
    //                mDB.execSQL("insert into m(name)values(?)",
    //                        new String[]{input});
                    datas = getData();
                    String [] string = new String [datas.size()] ;
                    datas.toArray(string);
    //                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,datas);
    //
    //                mAuto.setAdapter(adapter);

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("搜索结果");

                       builder.setSingleChoiceItems(string, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });

                    builder.show();

            }

    }
    protected void onDestroy(){
        if(mDB!=null){
            mDB.close();
        }
        super.onDestroy();
    }







}
