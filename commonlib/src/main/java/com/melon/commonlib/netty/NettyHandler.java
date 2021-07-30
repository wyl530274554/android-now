package com.melon.commonlib.netty;

import com.melon.commonlib.util.LogUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 通信助手
 */
@ChannelHandler.Sharable
public class NettyHandler extends SimpleChannelInboundHandler {
    private IMessageProcessor mMessageProcessor;

    private static final byte[] HEART_BYTES = "1".getBytes();

    public NettyHandler(IMessageProcessor messageProcessor) {
        mMessageProcessor = messageProcessor;

    }

    /**
     * 客户端收到消息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        try {
            ByteBuf buf = (ByteBuf) msg;
            int readableBytesLen = buf.readableBytes();

            if (readableBytesLen == 0) {
                return;
            }

            byte[] serverData = new byte[readableBytesLen];
            buf.readBytes(serverData);

            //断包后的结果处理
            mMessageProcessor.process(serverData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //问：加了这个ctx.fireChannelRead(msg)或ReferenceCountUtil.release(msg);   连接会自动断开，抛出refCnt: 0, decrement: 1
        //答：因为父类SimpleChannelInboundHandler已做好一切后续工作，不用再重复处理。这个方法内只需要处理业务即可
    }

    /**
     * 连接成功
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        LogUtil.d("channelActive");
    }

    /**
     * 连接断开
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        LogUtil.e("NettyHandler channelInactive，断线重连");
        Netty.getInstance().reConnect();
    }

    /**
     * 利用写空闲发送心跳检测消息
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                //写空闲 发送心跳
                ctx.channel().writeAndFlush(Unpooled.copiedBuffer(HEART_BYTES));

                // 注意：下面这两种方式，服务器收不到数据；
                // 即channel不能直接写原始bytes，需要用ByteBuf包装一下
                // ctx.channel().writeAndFlush(heart);
                // ctx.writeAndFlush(heart);
            }
        }
    }

    /**
     * 异常回调
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        LogUtil.e(cause.getMessage());
        ctx.close();
    }
}
