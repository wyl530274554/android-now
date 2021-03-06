package com.melon.unity;

import android.app.Application;
import android.os.Handler;
import android.speech.tts.TextToSpeech;

import com.melon.commonlib.listener.NettyConnectListener;
import com.melon.commonlib.util.ApiUtil;
import com.melon.commonlib.util.CrashHandler;
import com.melon.commonlib.util.LogUtil;
import com.melon.commonlib.util.SpUtil;
import com.melon.commonlib.util.TextToSpeechUtil;
import com.melon.commonlib.util.ToastUtil;
import com.melon.unity.net.SocketClient;

/**
 * 应用程序入口
 */
public class AppApplication extends Application {
    private static final Handler mHandler = new Handler();
    public static AppApplication mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("AppApplication onCreate");

        mApp = this;
        ApiUtil.initIp(this);
        ToastUtil.init(this);
        TextToSpeechUtil.init(this);
        CrashHandler.getInstance().init(this);

        boolean onlineOpen = SpUtil.getSettingBoolean(this, "online");
        if (onlineOpen) {
            SocketClient.getInstance().init(new NettyConnectListener());
        }
    }

    public static AppApplication getInstance() {
        return mApp;
    }

    public static Handler getHandler() {
        return mHandler;
    }

}
