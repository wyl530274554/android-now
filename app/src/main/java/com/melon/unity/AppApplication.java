package com.melon.unity;

import android.app.Application;

import com.melon.commonlib.util.ToastUtil;
import com.melon.unity.net.SocketClient;

/**
 * 应用程序入口Melon
 */
public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.init(this);
        SocketClient.getInstance().init(this);
    }
}
