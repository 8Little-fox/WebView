package com.example.webveiwtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class OtherActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        webView=findViewById(R.id.webView);
        WebSettings webSettings=webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //缓存
        webSettings.setJavaScriptEnabled(true);      //允许javascript 访问
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);   //允许弹窗
        webView.loadUrl("https://www.jianshu.com/p/91776e952a17");

    }
}
