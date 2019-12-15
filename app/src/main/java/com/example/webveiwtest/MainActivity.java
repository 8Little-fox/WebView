package com.example.webveiwtest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button=findViewById(R.id.but);
        webView=findViewById(R.id.web);
        WebSettings webSettings=webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //缓存
        webSettings.setJavaScriptEnabled(true);      //允许javascript 访问
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);   //允许弹窗
        webView.loadUrl("file:///android_asset/index.html");

        //实现Js 调用Android
        webView.addJavascriptInterface(new AndroidCall(this),"myService");
        //(点击button 按钮 调用js) 实现Android 调用JS
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                webView.evaluateJavascript("javascript:call('孙琳琳')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("alert1");
                builder.setMessage("message");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        result.confirm();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
                return true;
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Uri uri=Uri.parse(url);
                if (uri.getScheme().equals("js")){
                    if (uri.getAuthority().equals("webview")){
                        Toast.makeText(MainActivity.this,"js调用Android",Toast.LENGTH_LONG).show();
                        System.out.println("js调用Android ");

                        Set<String> connection=uri.getQueryParameterNames();
                        Iterator<String> iterator=connection.iterator();
                        while (iterator.hasNext()){
                            String next=iterator.next();
                            String value=uri.getQueryParameter(next);
                            System.out.println("key:"+next+"value"+value);
                        }
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                // 拦截输入框(原理同方式2)
                // 参数message:代表promt（）的内容（不是url）
                // 参数result:代表输入框的返回值
                Uri uri=Uri.parse(message);
                if (uri.getScheme().equals("js")){
                    if (uri.getAuthority().equals("demo")){
                        Toast.makeText(MainActivity.this,"js调用Android",Toast.LENGTH_LONG).show();
                        System.out.println("js调用Android onJsPrompt");

                        Set<String> connection=uri.getQueryParameterNames();
                        Iterator<String> iterator=connection.iterator();
                        while (iterator.hasNext()){
                            String next=iterator.next();
                            String value=uri.getQueryParameter(next);
                            System.out.println("key:"+next+"value"+value);
                        }
                        webView.loadUrl("javascript:returnResult(" + result + ")");
                    }
                    return true;
                }
                return super.onJsPrompt(view,url, message, defaultValue, result);
            }

        });
//        @Override
//        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//            return super.onJsAlert(view, url, message, result);
//        }
//// 拦截JS的确认框
//        @Override
//        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
//            return super.onJsConfirm(view, url, message, result);
//        }

    }
}
