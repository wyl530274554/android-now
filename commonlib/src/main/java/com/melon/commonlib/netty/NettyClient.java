package com.melon.commonlib.netty;

import com.melon.commonlib.listener.ConnectListener;
import com.melon.commonlib.util.LogUtil;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import static com.melon.commonlib.netty.NettyConfig.HEART_TIME_SECOND;

/**
 * netty Socket api
 * <p>
 * 需要两步
 * 1、init
 * 2、connect
 * <p>
 * TODO 实现多路连接
 */
public class NettyClient {
    /**
     * 单例
     */
    private static final NettyClient NETTY_CLIENT = new NettyClient();
    private NettyHandler mNettyHandler;
    /**
     * 主通信服务启动器
     */
    private Bootstrap mBootstrap;

    /**
     * 连接结果监听
     */
    private ConnectListener mConnectListener;
    /**
     * 主通信通道
     */
    private Channel mChannel;

    private String mHost;
    private int mPort;

    public void reConnect() {
        connect(mHost, mPort);
    }

    /**
     * 是否在线
     *
     * @param host 主机
     * @param port 端口
     */
    public boolean isOnline(String host, int port) {
        //TODO 按多路实现
        return mChannel != null && mChannel.isActive();
    }

    private NettyClient() {
    }

    public static NettyClient getInstance() {
        return NETTY_CLIENT;
    }

    public void setConnectListener(ConnectListener connectListener) {
        this.mConnectListener = connectListener;
    }

    public ConnectListener getConnectListener() {
        return mConnectListener;
    }

    public void init(IMessageProcessor processor) {
        mNettyHandler = new NettyHandler(processor);
        EventLoopGroup mWorkerGroup = new NioEventLoopGroup(4);

        //主通信Netty 初始化
        mBootstrap = new Bootstrap();
        mBootstrap.group(mWorkerGroup);
        mBootstrap.channel(NioSocketChannel.class);
        mBootstrap.option(ChannelOption.TCP_NODELAY, true);
        mBootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                //FIXME 当服务器未连接上时，这个initChannel会在每次的重连时重复的调用，这是个BUG？
                LogUtil.d("initChannel");
                ChannelPipeline pipeline = ch.pipeline();
                //心跳处理
                pipeline.addLast(new IdleStateHandler(0, HEART_TIME_SECOND, 0));
                //分隔符解码器，以0x7e断包
//                addDelimiterHandler(pipeline);
                pipeline.addLast(mNettyHandler);
            }

            @Override
            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                super.channelInactive(ctx);
                LogUtil.d("断线重连");
            }
        });
        LogUtil.d("netty init");
    }


    /**
     * 同步的连接请求(非子线程)
     */
    public void connect(final String host, final int port) {
        try {
            this.mHost = host;
            this.mPort = port;
            ChannelFuture future = mBootstrap.connect(host, port).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future != null && future.isSuccess()) {
                        //连接成功 (子线程中)
                        LogUtil.d("master server connect success");
                        mChannel = future.channel();

                        if (mConnectListener != null)
                            mConnectListener.onSuccess();

                    } else {
                        LogUtil.w("connect failed, reconnect...");
                        future.channel().eventLoop().schedule(new Runnable() {
                            @Override
                            public void run() {
                                LogUtil.d("connect failed, Loop connect");
                                connect(host, port);
                            }
                        }, 10, TimeUnit.SECONDS);
                    }
                }
            });

//            future.sync(); //同步的连接，android要求在子线程中连接网络，所以这个不能有
//            future.channel().closeFuture().sync();//加上这句，当前线程会阻塞，直到channel关闭；下面若有程序是暂时不会走的；
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("连接失败：" + e.getMessage());
        }
    }


    private void addDelimiterHandler(ChannelPipeline pipeline) {
        //分隔符解码器，以0x7e断包
//        ByteBuf delimiter = Unpooled.copiedBuffer(Constant.MSG_FLAG);
//        int maxFrameLength = 1024;
//        pipeline.addLast(new DelimiterBasedFrameDecoder(maxFrameLength, delimiter));
    }

    /**
     * 发送消息
     */
    public void sendData(byte[] msg, ChannelFutureListener futureListener) {
        if (mChannel == null || !mChannel.isActive()) {
            LogUtil.w("main channel inactive");
            return;
        }

        ByteBuf buf = mChannel.alloc().buffer(msg.length);
        buf.writeBytes(msg);
        ChannelFuture channelFuture = mChannel.writeAndFlush(buf);
        LogUtil.d("sendData: " + msg.length);

        if (futureListener != null) {
            channelFuture.addListener(futureListener);
        }
    }

    /**
     * 关闭主通信
     */
    public void close() {
        LogUtil.d("main channel closed");
        if (mChannel != null) {
            mChannel.close();
            mChannel = null;
        }
    }
}
