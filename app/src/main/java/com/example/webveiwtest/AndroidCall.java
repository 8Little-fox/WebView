package com.example.webveiwtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

public class AndroidCall {
    private Context context;

    public AndroidCall(Context context) {
        this.context = context;
    }
    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    private String work(String s){
        return "Hello ："+s;
    }

    @JavascriptInterface
    public void jump(){
        Intent intent=new Intent();
        intent.setClass(context,OtherActivity.class);
        context.startActivity(intent);
    }
}
