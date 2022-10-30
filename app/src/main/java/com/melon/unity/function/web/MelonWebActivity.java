package com.melon.unity.function.web;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.databinding.ViewDataBinding;

import com.melon.commonlib.BaseActivity;
import com.melon.unity.R;

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

    @Override
    protected void initView() {
        webView = findViewById(R.id.wv_web);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
    }
}
