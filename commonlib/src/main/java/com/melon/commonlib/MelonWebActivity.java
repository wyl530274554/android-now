package com.melon.commonlib;

import android.annotation.SuppressLint;
import android.webkit.WebView;

import androidx.databinding.ViewDataBinding;

public class MelonWebActivity extends BaseActivity {
    WebView webView;

    @Override
    protected void getViewModel() {

    }

    @Override
    protected void onDataBindingView(ViewDataBinding viewDataBinding) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        webView = findViewById(R.id.wv_web);

        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
    }
}
