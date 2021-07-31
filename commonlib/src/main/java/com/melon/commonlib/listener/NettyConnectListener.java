package com.melon.commonlib.listener;

import com.melon.commonlib.util.TextToSpeechUtil;

/**
 * Netty连接状态
 */
public class NettyConnectListener implements ConnectListener {
    @Override
    public void onSuccess() {
        // 播放声音
        TextToSpeechUtil.playVoice("欢迎回家");
    }
}
