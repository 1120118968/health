package com.example.mobilehomework;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class SportTaskActivity extends AppCompatActivity {

    //1.固定长度任务
    Spinner spinner;
    private TabHost tab;
    private ViewPager mTabPager;

    private TextView todayFinish;//今日完成
    private TextView txt_kcal;
    private TextView taskLength;//任务长度
    private TextView taskStep;//任务步数
    private TextView finishItem;//结束的项目

    private int kcal;//热量
    private int step;//步数
    private int length;//长度

    //2.自定义固定长度任务

    private EditText zBushu;//步数
    private EditText zQdian;//起点
    private EditText zZhong;//终点
    private EditText zChang;//长度
    private EditText zReliang;//热量
    private EditText zChengji;//成绩
    private EditText zQita;//其他

    private int zlength;
    private int zRe;
    private int zstep;

    private Banner banner;
    private ArrayList<String> list;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_task);

        banner=(Banner)this.findViewById(R.id.banner);
        list = new ArrayList<>();

        MyImageLoader myImageLoader=new MyImageLoader();
        localImages.add(R.mipmap.a);
        localImages.add(R.mipmap.b);
        localImages.add(R.mipmap.c);
        localImages.add(R.mipmap.d);

        for(int k = 0; k<localImages.size(); k++){
               list.add("第"+(k+1)+"张");
        }

        banner.setBannerTitles(list);//设置标题
        banner.isAutoPlay(true);//自动播放


        banner.setDelayTime(2000);//延迟时间
        banner.startAutoPlay();//自动播放
        banner.setImages(localImages);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(myImageLoader);
        //设置轮播的动画效果,里面有很多种特效,可以都看看效果。
        banner.setBannerAnimation(Transformer.ZoomOutSlide);
        //轮播图片的文字

        //设置轮播间隔时间

        //设置是否为自动轮播，默认是true

        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，居中显示
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载地址
        banner.setImages(localImages)
                //轮播图的监听
               // .setOnBannerListener((OnBannerListener) SportTaskActivity.this)
                //开始调用的方法，启动轮播图。
                .start();
        banner.setImages(localImages).setImageLoader(new MyImageLoader()).start();

        //启动Activity不弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //  instance = this;


        spinner = findViewById(R.id.spinner_lujing);

        taskLength = findViewById(R.id.task_length);
        todayFinish = findViewById(R.id.today_finish);
        txt_kcal = findViewById(R.id.kcal);
        taskStep = findViewById(R.id.task_tep);
        finishItem = findViewById(R.id.finish_item);

        zBushu = findViewById(R.id.zidingyi_bushu);
        zChang = findViewById(R.id.zidingyi_changdu);
        zChengji = findViewById(R.id.zidingyi_chengji);
        zQdian = findViewById(R.id.zidingyi_qidian);
        zZhong = findViewById(R.id.zidingyi_zhongdian);
        zQita = findViewById(R.id.zidingyi_qita);
        zReliang = findViewById(R.id.zidingyi_reliang);

        tab = (TabHost) this.findViewById(android.R.id.tabhost);
        mTabPager = findViewById(R.id.tabPager);
//
        tab.setup();

        //添加选项卡
        tab.addTab(tab.newTabSpec("tab1").setIndicator("固定长度路线", null).setContent(R.id.tab1));
        tab.addTab(tab.newTabSpec("tab2").setIndicator("自定义长度路线", null).setContent(R.id.tab2));


        spinner.setDropDownWidth(400); //下拉宽度
        spinner.setDropDownHorizontalOffset(100); //下拉的横向偏移
        spinner.setDropDownVerticalOffset(100); //下拉的纵向偏移



        final String[] spinnerItems = {"宿舍-E5","宿舍-F2"};
        //简单的string数组适配器：样式res，数组
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(SportTaskActivity.this,
                android.R.layout.simple_spinner_item, spinnerItems);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos == 0){

                    length = 500;

                    step = 600;

                    taskLength.setText("长度"+length);

                    taskStep.setText("步数"+step);

                    kcal = step/20;

                    txt_kcal.setText("热量"+kcal);
                    finishItem.setText("项目1结束");


                }
                else if (pos == 1){
                    length = 800;

                    step = 1066;

                    taskLength.setText("长度"+length);

                    taskStep.setText("步数"+step);

                    kcal = step/20;

                    txt_kcal.setText("热量"+kcal);
                    finishItem.setText("项目2结束");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
    public  void cal (View v){
     //   自定义长度计算热量
         String s= zChang.getText().toString();
         zlength = Integer.parseInt(s);
         zstep = (int) (zlength/0.75);
         zBushu.setText("步数"+zstep);
         zRe = zstep/20;
         zReliang.setText("热量"+zRe);
    }
    class MyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext())
                    .load(path)
                    .into(imageView);


        }
    }
}
