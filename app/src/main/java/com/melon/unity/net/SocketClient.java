package com.melon.unity.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;

import com.melon.commonlib.netty.Netty;
import com.melon.commonlib.util.LogUtil;


/**
 * socket连接客户端
 * 用法：SocketClient.getInstance().init();
 */
@SuppressLint("StaticFieldLeak")
public class SocketClient implements TextToSpeech.OnInitListener {
    private static final SocketClient sClient = new SocketClient();
    private static final String HOST = "192.168.100.234";
    private static final int PORT = 8066;
    private static boolean isInit;
    private static Context sContext;
    private TextToSpeech mSpeech;

    private SocketClient() {

    }

    public static SocketClient getInstance() {
        return sClient;
    }

    public void init(Context context) {
        if (isInit) {
            throw new RuntimeException("SocketClient不能重复初始化");
        }

        sContext = context;

        mSpeech = new TextToSpeech(sContext, this);

        Netty.getInstance().init(new ServerMessageProcessor());
        LogUtil.d("启动连接");
        Netty.getInstance().connect(HOST, PORT);
        Netty.getInstance().setConnectListener(new Netty.ConnectListener() {
            @Override
            public void onSuccess() {
                // 播放声音
                playVoice();
            }
        });
        isInit = true;
    }

    private void playVoice() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mSpeech.speak("欢迎回家", TextToSpeech.QUEUE_FLUSH, null);
            }
        }, 5000);
    }

    @Override
    public void onInit(int status) {
        LogUtil.d("onInit: " + status);
    }

    public boolean isOnline() {
        return Netty.getInstance().isOnline(HOST, PORT);
    }
}
