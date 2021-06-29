package com.melon.commonlib.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * 提示工具
 */
public class ToastUtil {
    @SuppressLint("StaticFieldLeak")
    private static Context sContext;
    private static Handler handler;

    /**
     * 必须初始化
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        sContext = context;
        handler = new Handler(sContext.getMainLooper());
    }

    /**
     * 短时间Toast显示
     */
    public static void toast(final String msg) {
        if (sContext == null) {
            throw new RuntimeException("Toast未初始化");
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(sContext, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
