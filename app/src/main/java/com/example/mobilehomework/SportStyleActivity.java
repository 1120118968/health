package com.example.mobilehomework;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SportStyleActivity extends AppCompatActivity {

    private LinearLayout mLinear;//
    private ImageSwitcher mSwitcher;//
    List<Integer> imgIds;//
    private ImageView[] imgViews;//
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_style);
        mLinear=(LinearLayout)findViewById(R.id.mLinear);
        mSwitcher = (ImageSwitcher)findViewById(R.id.mSwitcher);


        mSwitcher.setFactory(new ViewSwitcher.ViewFactory(){
            public android.view.View makeView(){
                ImageView img = new ImageView(SportStyleActivity.this);
                return img;
            }

        });
        imgIds=getImageIds();//
        mSwitcher.setImageResource(imgIds.get(0));
        init();//
    }
    @SuppressLint("NewApi")
    public void init(){
        imgViews=new ImageView[imgIds.size()];//

        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(60,80);
        layoutParams.setMargins(0, 0, 5, 0);

        for(int i=0;i<imgViews.length;i++){
            imgViews[i]=new ImageView(this);
            imgViews[i].setId(imgIds.get(i));
            imgViews[i].setBackgroundResource(R.drawable.bg);


            imgViews[i].setImageResource(imgIds.get(i));
            imgViews[i].setLayoutParams(layoutParams);
            imgViews[i].setOnClickListener(new MyListener());
            if(i!=0){
                imgViews[i].setImageAlpha(100);

            }else{
                imgViews[i].setImageAlpha(255);
            }

            mLinear.addView(imgViews[i]);
           // Toast.makeText(SportStyleActivity.this,"你已经浏览完所有运动", Toast.LENGTH_LONG).show();
        }
    }
    private class MyListener implements View.OnClickListener {


        @SuppressLint("ResourceType")
        @Override
        public void onClick(android.view.View v) {
            // TODO Auto-generated method stub
            mSwitcher.setImageResource(v.getId());
            setAlpha(imgViews);
            ((ImageView)v).setImageAlpha(255);

            switch (v.getId()){
                case 0:
                    Toast.makeText(SportStyleActivity.this,"你选择的运动方式为乒乓球", Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Toast.makeText(SportStyleActivity.this,"你选择的运动方式为冲浪", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(SportStyleActivity.this,"你选择的运动方式为骑自行车", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(SportStyleActivity.this,"你选择的运动方式为跑步", Toast.LENGTH_LONG).show();
                    break;
                case 4:
                    Toast.makeText(SportStyleActivity.this,"你选择的运动方式为散步", Toast.LENGTH_LONG).show();
                    break;
                case 5:
                    Toast.makeText(SportStyleActivity.this,"你选择的运动方式为踢足球", Toast.LENGTH_LONG).show();
                    break;
                case 6:
                    Toast.makeText(SportStyleActivity.this,"你选择的运动方式为滑雪", Toast.LENGTH_LONG).show();
                    break;

                default:
                    Toast.makeText(SportStyleActivity.this,"你选择的运动方式为高尔夫球", Toast.LENGTH_LONG).show();
                    break;

            }
        }


    }
    @SuppressLint("NewApi")
    public void setAlpha(ImageView[] imageViews){
        for(int i=0;i<imageViews.length;i++){
            imageViews[i].setImageAlpha(100);
        }
    }
    //
    @SuppressLint("NewApi")
    public List<Integer>getImageIds(){
        List<Integer> imageIds=new ArrayList<Integer>();
        try{
            //
            Field[] drawableFields = R.mipmap.class.getFields();
            //
            //
            for(Field field : drawableFields){
                if(field.getName().startsWith("s_")){
                    imageIds.add(field.getInt(R.mipmap.class));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return imageIds;
    }
}
