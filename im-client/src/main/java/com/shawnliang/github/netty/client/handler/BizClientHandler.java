package com.shawnliang.github.netty.client.handler;

import com.shawnliang.github.netty.client.cache.UserSession;
import com.shawnliang.github.netty.core.dto.MsgGroupReq;
import com.shawnliang.github.netty.core.dto.MsgOneReq;
import com.shawnliang.github.netty.core.dto.UserDataInfo;
import com.shawnliang.github.netty.core.message.MessageFactory;
import com.shawnliang.github.netty.core.message.MessageResp;
import com.shawnliang.github.netty.core.message.MsgReq;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/3
 */
@Sharable
public class BizClientHandler extends SimpleChannelInboundHandler<MessageResp> {

    private static final Logger logger = LogManager.getLogger(BizClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResp obj) throws Exception {
        MessageResp resp = (MessageResp) obj;
        // 收到来自服务端的resp
        Boolean success = resp.getSuccess();
        if (!success) {
            logger.info("receive result is error, data is: {}", resp);
            return;
        }

        Object result = resp.getResult();
        if (result instanceof Map) {
            Map<String, UserDataInfo> clientSession = (Map<String, UserDataInfo>) result;
            clientSession.forEach((userName, userDataInfo) -> {
                UserSession.saveUser(userName, userDataInfo);
                UserSession.printAll();
            });
        }
        else if (result instanceof MsgOneReq) {
            logger.info("收到消息私聊消息——: [{}]", result);
        }
        else if (result instanceof MsgGroupReq) {
            logger.info("收到群组消息——: [{}]", result);
        }
    }

    /**
     *
     * @param ctx
     * @param evt
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            MsgReq heartBeat = MessageFactory.genHeartBeat();
            logger.info("client trigger event, send heartbeat to server: {}", heartBeat);

            ctx.writeAndFlush(heartBeat)
                    .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
