package com.shawnliang.github.netty.server.handler;

import com.shawnliang.github.netty.core.message.MessageResp;
import com.shawnliang.github.netty.core.message.MsgReq;
import com.shawnliang.github.netty.server.data.ClientRegisterInfo;
import com.shawnliang.github.netty.server.strategy.MsgRoute;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/4
 */
public class ClientToServerHandler extends SimpleChannelInboundHandler<MsgReq>{
    private static final Logger logger = LogManager.getLogger(ClientToServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgReq msg) throws Exception {
        logger.info("server received msg {}", msg);
        MessageResp resp = MsgRoute.processor(msg, ctx.channel());
        logger.info("server resp is {}", resp);

        ctx.writeAndFlush(resp);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("client is register, channel: {}", ctx.channel());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("有客户端注销：{}", ctx.channel());

        // 移除客户端
        ClientRegisterInfo.removeChannel((NioSocketChannel) ctx.channel());

        // 打印客户端
        ClientRegisterInfo.printAllRegister();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            logger.info("32秒内未收到来自client 的请求。。关闭channel连接");

            // 关闭channel
            Channel channel = ctx.channel();
            channel.close();

            // 移除客户端
            ClientRegisterInfo.removeChannel((NioSocketChannel) channel);
            // 打印客户端
            ClientRegisterInfo.printAllRegister();
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * TODO 不活跃状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        int hashCode = ctx.channel().hashCode();
        super.channelInactive(ctx);
    }
}
