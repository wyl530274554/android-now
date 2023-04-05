package com.melon.commonlib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.melon.commonlib.MelonWebActivity;
import com.melon.commonlib.util.CommonUtil;

/**
 * 自定义webView
 */
public class MelonWebView extends WebView implements View.OnLongClickListener {
    private final Context mContext;

    public MelonWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        initWebParams();
    }

    private void initView() {
        this.setOnLongClickListener(this);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebParams() {
        setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return !url.startsWith("http");
            }
        });
        setWebChromeClient(new WebChromeClient());
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true); // 关键点
        settings.setDomStorageEnabled(true);
        // Https嵌套http图片问题
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        //不加载图片
        settings.setBlockNetworkImage(true);
    }


    @Override
    public boolean onLongClick(View view) {
        WebView.HitTestResult result = ((WebView) view).getHitTestResult();
        if (null == result) {
            return false;
        }
        int type = result.getType();
        String extra = result.getExtra();
        switch (type) {
            // 选中的文字类型
            case WebView.HitTestResult.EDIT_TEXT_TYPE:
                break;
            // 处理拨号
            case WebView.HitTestResult.PHONE_TYPE:
                break;
            // 处理Email
            case WebView.HitTestResult.EMAIL_TYPE:
                break;
            // 　地图类型
            case WebView.HitTestResult.GEO_TYPE:
                break;
            // 带有链接的图片类型
            case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                // 处理长按图片的菜单项
            case WebView.HitTestResult.IMAGE_TYPE:
                // 超链接
            case WebView.HitTestResult.SRC_ANCHOR_TYPE:
                // 另起一页
                if (!CommonUtil.isEmpty(extra)) {
                    openNewWindow(extra, mContext);
                }
                break;
            //未知
            case WebView.HitTestResult.UNKNOWN_TYPE:
                return false;
            default:
        }
        return true;
    }

    /**
     * 开启新窗口
     */
    private void openNewWindow(String url, Context ctx) {
        Intent intent = new Intent(ctx, MelonWebActivity.class);
        intent.putExtra("url", url);
        ctx.startActivity(intent);
    }

}
