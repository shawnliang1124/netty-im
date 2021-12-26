package com.shawnliang.github.netty.server.handler;

import com.shawnliang.github.netty.core.constant.Constant;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/1
 */
public class ServerHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // TODO 增加handler
        ChannelPipeline pipeline = ch.pipeline();

        // 添加server心跳任务，32S内没有收到，证明client已经关闭
//        pipeline.addLast(new IdleStateHandler(0 , 0 ,32, TimeUnit.SECONDS));
        pipeline.addLast(ServerHandler.this.buildDelimiterDecoder());

        pipeline.addLast(new ServerMsgDecoder());
        pipeline.addLast(new ServerMsgEncoder());

        pipeline.addLast(new ClientToServerHandler());
    }

    private DelimiterBasedFrameDecoder buildDelimiterDecoder() {
        ByteBuf splitBuffer = Unpooled.copiedBuffer(Constant.SPLIT_SYMBOL.getBytes());
        return new DelimiterBasedFrameDecoder(65536, splitBuffer);
    }
}
