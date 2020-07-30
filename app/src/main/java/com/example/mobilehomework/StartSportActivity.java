package com.example.mobilehomework;


import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.app.Activity;

import android.content.pm.PackageManager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;


import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;


public class StartSportActivity extends Activity {

    LocationClient mLocationListener;//gps location
    BaiduMap Baidumap;  //baidu map
    boolean isFirstLocate;
    TextView showData;
    MapView showBdmap;//baidumap view
    Button reli;
    Button weixing;
    Button lukuang;
    Button jiaotong;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_start_sport);



        List<String> permissionlist = new ArrayList<>();

        //check self permission
        if (ContextCompat.checkSelfPermission(StartSportActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(StartSportActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.ACCESS_WIFI_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.CHANGE_WIFI_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.INTERNET);
        }
        if (!permissionlist.isEmpty()) {
            String[] permissions = permissionlist.toArray(new String[permissionlist.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {
            initData();
            reli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Baidumap = showBdmap.getMap();
//卫星地图
                    Baidumap.setBaiduHeatMapEnabled(true);
                }
            });
            lukuang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Baidumap = showBdmap.getMap();
//卫星地图
                    //开启交通图
                    Baidumap.setTrafficEnabled(true);
                }
            });
            jiaotong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Baidumap.setTrafficEnabled(true);
                    Baidumap.setCustomTrafficColor("#ffba0101", "#fff33131", "#ffff9e19", "#00000000");
//  对地图状态做更新，否则可能不会触发渲染，造成样式定义无法立即生效。
                    MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(13);
                    Baidumap.animateMapStatus(u);
                }
            });
            weixing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Baidumap = showBdmap.getMap();
//卫星地图
                    Baidumap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意才能使用", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    initData();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }
    public void initData() {
            showData = findViewById(R.id.showData);
            //下面三行添加代码         //初始化控件
            showBdmap = findViewById(R.id.bmapView);

            reli = findViewById(R.id.reli);
            lukuang = findViewById(R.id.lukuang);
            jiaotong = findViewById(R.id.jiaotong);
            weixing = findViewById(R.id.weixing);
            //标记点

            //使用控件得到百度地图实例
            Baidumap = showBdmap.getMap();
            //百度地图设置是否显示我的位置
            Baidumap.setMyLocationEnabled(true);
            //设置位置客户端选项
            LocationClientOption option = new LocationClientOption();
            //设置位置取得模式  （这里是指定为设备传感器也就是 GPS 定位）
            option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
            //设置 间隔扫描的时间  也就是 位置时隔多长时间更新
            option.setScanSpan(1000);        //设置 是否需要地址 （需要联网取得 百度提供的位置信息）
            option.setIsNeedAddress(true);
            mLocationListener = new LocationClient(getApplicationContext());
            mLocationListener.setLocOption(option);
            mLocationListener.registerLocationListener(new BDAbstractLocationListener() {
                @Override
                public void onReceiveLocation(BDLocation bdLocation) {
                    StringBuilder currentPosition = new StringBuilder();
                    //获取经纬度
                    currentPosition.append("纬度：").append(bdLocation.getLatitude()).append("\n");
                    currentPosition.append("经线：").append(bdLocation.getLongitude()).append("\n");
                    //获取详细地址信息
                    currentPosition.append("国家：").append(bdLocation.getCountry()).append("\n");
                    currentPosition.append("省：").append(bdLocation.getProvince()).append("\n");
                    currentPosition.append("市：").append(bdLocation.getCity()).append("\n");
                    currentPosition.append("区：").append(bdLocation.getDistrict()).append("\n");
                    currentPosition.append("街道：").append(bdLocation.getStreet()).append("\n");
                    //获取定位方式
                    currentPosition.append("定位方式：");
                    if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                        currentPosition.append("GPS 定位");
                    } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                        currentPosition.append("网络 定位");
                    }                 //更新地图位置 （添加代码）
                    if (bdLocation.getLocType() == BDLocation.TypeGpsLocation ||
                            bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                        navigateTo(bdLocation);
                    }
                    showData.setText(currentPosition);
                }
            });

            mLocationListener.start();

            LatLng point = new LatLng(39.963175, 116.400244);
        //构建Marker图标
          //  BitmapDescriptor bitmap = BitmapDescriptorFactory
           //         .fromResource(R.drawable.view_icon_data_emtry);
        //构建MarkerOption，用于在地图上添加Marker
          //  OverlayOptions option1 = new MarkerOptions()
//                    .position(point)
//                    .icon(bitmap);
//        //在地图上添加Marker，并显示
//            Baidumap.addOverlay(option1);
                    //构建折线点坐标
            List<LatLng> points = new ArrayList<LatLng>();
            points.add(new LatLng(39.965,116.404));
            points.add(new LatLng(39.925,116.454));
            points.add(new LatLng(39.955,116.494));
            points.add(new LatLng(39.905,116.554));
            points.add(new LatLng(39.965,116.604));

            List<Integer> colors = new ArrayList<>();
            colors.add(Integer.valueOf(Color.BLUE));
            colors.add(Integer.valueOf(Color.RED));
            colors.add(Integer.valueOf(Color.YELLOW));
            colors.add(Integer.valueOf(Color.GREEN));

        //设置折线的属性
            OverlayOptions mOverlayOptions = new PolylineOptions()
                    .width(10)
                    .color(0xAAFF0000)
                    .points(points)
                    .colorsValues(colors);//设置每段折线的颜色

        //在地图上绘制折线
        //mPloyline 折线对象
            Overlay mPolyline = Baidumap.addOverlay(mOverlayOptions);

                    //圆心位置
            LatLng center = new LatLng(39.90923, 116.447428);

        //构造CircleOptions对象
            CircleOptions mCircleOptions = new CircleOptions().center(center)
                    .radius(1400)
                    .fillColor(0xAA0000FF) //填充颜色
                    .stroke(new Stroke(5, 0xAA00ff00)); //边框宽和边框颜色

        //在地图上显示圆
            Overlay mCircle = Baidumap.addOverlay(mCircleOptions);

            LatLng llText = new LatLng(39.86923, 116.397428);

        //构建TextOptions对象
            OverlayOptions mTextOptions = new TextOptions()
                    .text("运动点") //文字内容
                    .bgColor(0xAAFFFF00) //背景色
                    .fontSize(24) //字号
                    .fontColor(0xFFFF00FF) //文字颜色
                    .rotate(-30) //旋转角度
                    .position(llText);

        //在地图上显示文字覆盖物
            Overlay mText = Baidumap.addOverlay(mTextOptions);

            InfoWindow mInfoWindow;
            Button button = new Button(getApplicationContext());
          //  button.setBackgroundResource(R.drawable.app_item_bg);
            button.setText("定位点");

            //构造InfoWindow
            //point 描述的位置点
            //-100 InfoWindow相对于point在y轴的偏移量
                mInfoWindow = new InfoWindow(button, point, -100);

            //使InfoWindow生效
                Baidumap.showInfoWindow(mInfoWindow);

            //构造Icon列表
        // 初始化bitmap 信息，不用时及时 recycle
            BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(R.drawable.ic_category_12);
            BitmapDescriptor bdB = BitmapDescriptorFactory.fromResource(R.drawable.ic_category_17);
            BitmapDescriptor bdC = BitmapDescriptorFactory.fromResource(R.drawable.ic_category_25);

            ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
            giflist.add(bdA);
            giflist.add(bdB);
            giflist.add(bdC);
        //Marker位置坐标
            LatLng llD = new LatLng(39.906965, 116.401394);
        //构造MarkerOptions对象
            MarkerOptions ooD = new MarkerOptions()
                    .position(llD)
                    .icons(giflist)
                    .zIndex(0)
                    .period(20);//定义刷新的帧间隔

        //在地图上展示包含帧动画的Marker
            Overlay mMarkerD = (Marker) (Baidumap.addOverlay(ooD));



        }


    private void navigateTo(BDLocation location) {
        //只是让它更新一次
        if (isFirstLocate) {
            //获得经纬度 装进 LatLng
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            //设置地图更新位置到 MapStatusUpdate 里面
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);            //地图更新
            Baidumap.animateMapStatus(update);            //设置缩放比例
            update = MapStatusUpdateFactory.zoomTo(16f);            //地图更新
            Baidumap.animateMapStatus(update);            //设置旗标为 false  以后不再执行位置更新
            isFirstLocate = false;
        }
        // 构建 我的位置
        MyLocationData.Builder builder = new MyLocationData.Builder();        //获取经纬度
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLongitude());
        //构建
        MyLocationData locationData = builder.build();
        //百度地图设置
        Baidumap.setMyLocationData(locationData);
    }
    @Override
    protected void onResume() {
        initData();
        super.onResume();
        showBdmap.onResume();
    }
    @Override
    protected void onPause() {
        initData();
        super.onPause();
        showBdmap.onPause();
    }
    @Override
    protected void onDestroy() {
        initData();
        super.onDestroy();
        mLocationListener.stop();
        showBdmap.onDestroy();
        Baidumap.setMyLocationEnabled(false);
    }
}


