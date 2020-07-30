package com.example.mobilehomework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageSwitcherActivity extends AppCompatActivity {

    private MyImageTopView myImageTopView;
    private LinearLayout linearLayout;
    private int[] imgIds = new int[]{R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,
            R.drawable.pic5,R.drawable.pic6,R.drawable.pic7};
    public ImageView[] imgViews = new ImageView[imgIds.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_switcher);
        linearLayout = findViewById(R.id.mBottomView);
        myImageTopView = findViewById(R.id.mTopView);
        initBottom();
        myImageTopView.initImages(imgIds);
    }

    private void initBottom() {
        for (int i =0 ;i<imgViews.length;i++){
            imgViews[i] = new ImageView(this);
            if (i==0){
                imgViews[i].setImageResource(R.drawable.choosed);
            }else {
                imgViews[i].setImageResource(R.drawable.unchoosed);
            }
            imgViews[i].setPadding(15,0,0,0);
            imgViews[i].setId(i);
            imgViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetImg();
                    ((ImageView)v).setImageResource(R.drawable.choosed);
                    myImageTopView.scrollToImage(v.getId());
                }
            });
            linearLayout.addView(imgViews[i]);
        }
    }

    private void resetImg() {
        for (int i=0; i<imgViews.length;i++){
            imgViews[i].setImageResource(R.drawable.unchoosed);
        }
    }
}
