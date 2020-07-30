package com.example.mobilehomework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView webView;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        webView=findViewById(R.id.webView1);

        WebSettings webSettings = webView.getSettings();
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
       //  让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        webSettings.setAppCacheEnabled(true);
        // 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 设置缓存路径
//        webSettings.setAppCachePath("");
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(true);
        // 设置默认字体大小
        webSettings.setDefaultFontSize(12);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://m.weather.com.cn/m/pn12/weather.htm");

        Button bj=(Button)findViewById(R.id.bj);
        bj.setOnClickListener(this);
        Button sh=(Button)findViewById(R.id.sh);
        sh.setOnClickListener(this);
        Button heb=(Button)findViewById(R.id.heb);
        heb.setOnClickListener(this);
        Button cc=(Button)findViewById(R.id.cc);
        cc.setOnClickListener(this);
        Button sy=(Button)findViewById(R.id.sy);
        sy.setOnClickListener(this);
        Button gz=(Button)findViewById(R.id.gz);
        gz.setOnClickListener(this);
    }

    private void openUrl(String id){
        webView.loadUrl("http://weather.com.cn/weather/"+id+".shtml");

    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch(arg0.getId()){
            case R.id.bj:
                openUrl("101010100");
                break;
            case R.id.sh:
                openUrl("101020100");
                break;
            case R.id.heb:
                openUrl("101050101");
                break;
            case R.id.cc:
                openUrl("101060101");
                break;
            case R.id.sy:
                openUrl("101070101");
                break;
            case R.id.gz:
                openUrl("101280101");


        }
    }

}
