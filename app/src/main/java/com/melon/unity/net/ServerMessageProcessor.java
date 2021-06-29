package com.melon.unity.net;

import com.melon.commonlib.netty.IMessageProcessor;
import com.melon.commonlib.util.LogUtil;

/**
 * 解析处理服务发来的数据
 */
public class ServerMessageProcessor implements IMessageProcessor {
    @Override
    public void process(byte[] msgBytes) throws Exception {
        LogUtil.d("接收到数据:" + msgBytes.length);
    }
}
