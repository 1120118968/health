package com.example.mobilehomework;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.today.step.lib.ISportStepInterface;
import com.today.step.lib.TodayStepManager;
import com.today.step.lib.TodayStepService;

import java.util.ArrayList;
import java.util.List;

public class PageSwitcher extends AppCompatActivity implements SensorEventListener {
    SensorManager mSensorManager;
    //第一个页面步数的获取
    Sensor stepCounter;
    View viewPager;
    View viewPager2;
    private static String TAG = "PageSwitcher";

    private static final int REFRESH_STEP_WHAT = 0;

    //循环取当前时刻的步数中间的间隔时间
    private long TIME_INTERVAL_REFRESH = 3000;

    private Handler mDelayHandler = new Handler(new PageSwitcher.TodayStepCounterCall());
    private int mStepSum;

    private ISportStepInterface iSportStepInterface;

    private TextView mStepArrayTextView;

    private TextView timeTextView;
    SQLiteDatabase db;
    String name;
    int step;
    int time;

    //第二个页面控件
    WebView webView;
    TextView textView;
    CircleBar circleBar;
    private MyDataBaseHelper dbHelper;
    String selectSql = "select * from step";
    Button challenge;


    //第三个页面的获取

   // View viewPager3;//第三个页面

   //主页面

    private ViewPager mViewPager;
    private PagerTabStrip mTabs;
    private LinearLayout mImgs;
    private int[] layouts = new int []{R.layout.activity_step,R.layout.framelayout,R.layout.activity_food};
    private String[] titles = new String[] {"当前步数","今日步数","热量计算"};
    private ImageView[] mImgViews = new ImageView[layouts.length];
    private List<View> views = new ArrayList<View>();
    private List<String> paperTitles = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_page_switcher);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        // getSensorList用于列出设备支持的所有sensor列表
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        Log.i(TAG,"Sensor size:"+sensorList.size());
        for (Sensor sensor : sensorList) {
            Log.i(TAG,"Supported Sensor: "+sensor.getName());
        }
        // 获取计步器sensor
        stepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(stepCounter != null){
            // 如果sensor找到，则注册监听器
            mSensorManager.registerListener(this,stepCounter,1000000);
        }
        else{
            Log.e(TAG,"no step counter sensor found");
        }

        mImgs =  findViewById(R.id.mImgs);
        mViewPager = findViewById(R.id.viewPage);
        mTabs = findViewById(R.id.tab);
        mTabs.setTextSpacing(300);
        init();
        mViewPager.setAdapter(new MyPagerAdapter());
        initImg();
        mViewPager.setOnPageChangeListener(new MyPagerChangeListener());

    }

    private void initImg() {
        for(int i =0 ; i<mImgViews.length;i++){
            mImgViews[i]  = new ImageView(PageSwitcher.this);
            if (i == 0){
                mImgViews[i].setImageResource(R.drawable.ic_category_12);
            }else{
                mImgViews[i].setImageResource(R.drawable.ic_category_17);
            }
            mImgViews[i].setPadding(20,0,0,0);
            mImgViews[i].setId(i);
            mImgViews[i].setOnClickListener(mOnClickListener);
            mImgs.addView(mImgViews[i]);

        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            resetImg();
            ((ImageView)v).setImageResource(R.drawable.ic_category_25);
            mViewPager.setCurrentItem(v.getId());
        }
    };

    private void resetImg() {
        for( int i =0 ;i<mImgViews.length; i++){
            mImgViews[i].setImageResource(R.drawable.ic_category_12);
        }
    }

    private void init() {
        for(int i = 0 ; i<layouts.length ;i++){

            View view = getLayoutInflater().inflate(layouts[i],null);
            paperTitles.add(titles[i]);

            views.add(view);

        }
        viewPager = LayoutInflater.from(this).inflate(R.layout.activity_step, null);
        //首页
        TodayStepManager.startTodayStepService(getApplication());
        challenge = viewPager.findViewById(R.id.challenge);
        timeTextView = viewPager.findViewById(R.id.movement_total_steps_time_tv);
        mStepArrayTextView = viewPager.findViewById(R.id.movement_total_steps_tv);

        Intent intent = new Intent(this, TodayStepService.class);
        startService(intent);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //Activity和Service通过aidl进行通信
                iSportStepInterface = ISportStepInterface.Stub.asInterface(service);
                try {
                    mStepSum = iSportStepInterface.getCurrentTimeSportStep();
                    updateStepCount();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                mDelayHandler.sendEmptyMessageDelayed(REFRESH_STEP_WHAT, TIME_INTERVAL_REFRESH);

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);

        //计时器
        mhandmhandlele.post(timeRunable);

    //    mStepArrayTextView.setText(step);


        MyDataBaseHelper dbHelper = new MyDataBaseHelper(PageSwitcher.this, "jibu_db",null,1);
        db = dbHelper.getWritableDatabase();
        //开启计步Service，同时绑定Activity进行aidl通信
        //        //步数表
        //        String stepsql = "create table step(name varchar(20),step int(20),time int(20))";
        Intent i1 = getIntent();
        name = i1.getStringExtra("name");


        //写入数据
        ContentValues values = new ContentValues();
        values.put("name",name);

        values.put("step",step);

        values.put("time",time);
        db.insert("step",null,values);
        //第二个页面控件初始化

        //然后通过子view对象，获取其内的控件ID
        viewPager2 = LayoutInflater.from(this).inflate(R.layout.framelayout, null);
        webView = viewPager2.findViewById(R.id.webview);
        textView = viewPager2.findViewById(R.id.textview);
        circleBar = viewPager2.findViewById(R.id.circle);
        webView.loadUrl("http://www.gpsspg.com/maps.htm");
        //第三个页面初始化

      //  viewPager3 = LayoutInflater.from(this).inflate(R.layout.activity_main, null);


    }



    private void updateStepCount() {
        //步数更新
        Log.e(TAG, "updateStepCount : " + mStepSum);
        TextView stepTextView = viewPager.findViewById(R.id.movement_total_steps_tv);
        if (mStepSum == 10000){
            Toast.makeText(PageSwitcher.this,"你已经走满一万步，加油",Toast.LENGTH_SHORT).show();
        }
        stepTextView.setText(mStepSum + "步");
        circleBar.update(mStepSum,1000);

    }

    public void tongbu(View view) {

                //获取所有步数列表
                if (null != iSportStepInterface) {
                    try {
                        String stepArray = iSportStepInterface.getTodaySportStepArray();
                        mStepArrayTextView.setText(stepArray);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

    }
    public void task(View v){
        Intent i = new Intent(PageSwitcher.this,SportTaskActivity.class);
        startActivity(i);
    }
    public void friend(View v){
        Intent i = new Intent(PageSwitcher.this,FriendActivity.class);
        startActivity(i);
    }
    public void weather(View v){
        Intent i = new Intent(PageSwitcher.this,WeatherActivity.class);
        startActivity(i);
    }


    /*****************计时器*******************/
    private Runnable timeRunable = new Runnable() {
        @Override
        public void run() {

            currentSecond = currentSecond + 1000;
            timeTextView.setText(getFormatHMS(currentSecond));
            time = (int) currentSecond;

            if (!isPause) {
                //递归调用本runable对象，实现每隔一秒一次执行任务
                mhandmhandlele.postDelayed(this, 1000);
            }
        }
    };
    //计时器
    private Handler mhandmhandlele = new Handler();
    private boolean isPause = false;//是否暂停
    private long currentSecond = 0;//当前毫秒数

    public static String getFormatHMS(long time) {
        time = time / 1000;//总秒数
        int s = (int) (time % 60);//秒
        int m = (int) (time / 60);//分
        int h = (int) (time / 3600);//秒
        return String.format("%02d:%02d:%02d", h, m, s);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float mSteps = event.values[0];

        //mStepArrayTextView.setText((int) mSteps);


        Log.i(TAG,"Detected step changes:"+event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class MyPagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            resetImg();

            mImgViews[i].setImageResource(R.drawable.ic_category_12);
            if (i == 2){
                Intent intent = new Intent(PageSwitcher.this,FoodActivity.class);
                startActivity(intent);
            }

        }

        private void resetImg() {
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return paperTitles.get(position);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ((ViewPager)container).addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            ((ViewPager)container).removeView(views.get(position));
        }
    }
    public View start(View v) {
        webView.loadUrl("http://www.gpsspg.com/maps.htm");
        dbHelper = new MyDataBaseHelper(PageSwitcher.this, "jibu_db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        Cursor cursor =  db.execSQL(selectSql);
        Cursor cursor = db.query("step", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                //        //步数表
                //        String stepsql = "create table step(name varchar(20),step int(20),time int(20))";
                String name = cursor.getString(cursor.getColumnIndex("name"));

                int step = cursor.getInt(cursor.getColumnIndex("step"));
                int time = cursor.getInt(cursor.getColumnIndex("time"));
                textView.setText(name + "你今天运动了" + step + "步" + "花费了" + time + "时间");

            } while (cursor.moveToNext());
        }
        cursor.close();
       Intent i = new Intent(PageSwitcher.this,StartSportActivity.class);
      //  Intent i = new Intent(PageSwitcher.this,FoodActivity.class);
        startActivity(i);
        return null;
    }
    class TodayStepCounterCall implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_STEP_WHAT: {
                    //每隔500毫秒获取一次计步数据刷新UI
                    if (null != iSportStepInterface) {

                        try {
                            step = iSportStepInterface.getCurrentTimeSportStep();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        if (mStepSum != step) {
                            mStepSum = step;
                            updateStepCount();
                        }
                    }
                    mDelayHandler.sendEmptyMessageDelayed(REFRESH_STEP_WHAT, TIME_INTERVAL_REFRESH);

                    break;
                }
            }
            return false;
        }
    }
}

