package com.melon.commonlib.netty;

/**
 * 消息处理器
 */
public interface IMessageProcessor {

    /**
     * 处理
     */
    void process(byte[] msgBytes) throws Exception;
}
