package com.melon.unity.function.settings;

import com.melon.unity.net.SocketClient;

/**
 * M层
 * 负责数据获取
 */
public class SettingsModel {

    public boolean isOnline() {
        return SocketClient.getInstance().isOnline();
    }
}
