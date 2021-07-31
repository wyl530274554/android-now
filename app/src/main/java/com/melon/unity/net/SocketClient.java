package com.melon.unity.net;

import android.annotation.SuppressLint;

import com.melon.commonlib.listener.ConnectListener;
import com.melon.commonlib.netty.NettyClient;
import com.melon.commonlib.util.LogUtil;


/**
 * socket连接客户端
 * 用法：SocketClient.getInstance().init();
 */
@SuppressLint("StaticFieldLeak")
public class SocketClient {
    private static final SocketClient sClient = new SocketClient();
    private static final String HOST = "192.168.100.234";
    private static final int PORT = 8066;
    private static boolean isInitialized;

    private SocketClient() {

    }

    public static SocketClient getInstance() {
        return sClient;
    }

    public void init(ConnectListener listener) {
        if (isInitialized) {
            throw new RuntimeException("SocketClient不能重复初始化");
        }

        NettyClient.getInstance().init(new ServerMessageProcessor());
        LogUtil.d("启动连接");
        NettyClient.getInstance().connect(HOST, PORT);
        NettyClient.getInstance().setConnectListener(listener);
        isInitialized = true;
    }

    public boolean isOnline() {
        return NettyClient.getInstance().isOnline(HOST, PORT);
    }
}
