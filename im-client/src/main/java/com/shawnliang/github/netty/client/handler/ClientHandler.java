package com.shawnliang.github.netty.client.handler;

import com.shawnliang.github.netty.core.constant.Constant;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * Description : 单例的clientHandler  .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/3
 */
public class ClientHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(this.buildDelimiterDecoder());
        // 增加心跳检测，30s，channel上没有发生读写事件，会触发userEventTriggered事件
//        pipeline.addLast(new IdleStateHandler
//                (0, 0, 30, TimeUnit.SECONDS));

        pipeline.addLast(new ClientMsgDecoder());
        pipeline.addLast(new ClientMsgEncoder());

        pipeline.addLast(new BizClientHandler());
    }

    private DelimiterBasedFrameDecoder buildDelimiterDecoder() {
        ByteBuf splitBuffer = Unpooled.copiedBuffer(Constant.SPLIT_SYMBOL.getBytes());
        return new DelimiterBasedFrameDecoder(65536, splitBuffer);
    }


}
