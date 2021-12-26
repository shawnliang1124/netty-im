package com.shawnliang.github.netty.client.action.impl;

import com.shawnliang.github.netty.client.action.IAction;
import com.shawnliang.github.netty.client.context.BizContext;
import com.shawnliang.github.netty.client.init.ClientInitialize;
import com.shawnliang.github.netty.core.message.MessageFactory;
import com.shawnliang.github.netty.core.message.MsgReq;
import io.netty.channel.socket.SocketChannel;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/26
 */
public class IActionImpl implements IAction {

    private static final Logger logger = LogManager.getLogger(ClientInitialize.class);

    private SocketChannel channel;

    public IActionImpl(SocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void sendMsgToOne(String to, String data) {
        if (StringUtils.isBlank(to)
                || StringUtils.isBlank(data)) {
            throw new RuntimeException("发送体为空，请重新输入");
        }

        MsgReq msgReq = MessageFactory.genMsgReqToOne(BizContext.getTotalUsername(), to, data);
        logger.info("send msg to:{}, data is: {}", to, data);

        channel.writeAndFlush(msgReq);
    }

    @Override
    public void sendMsgToGroup(String data) {
        if (StringUtils.isEmpty(data)) {
            throw new RuntimeException("发送体为空，请重新输入");
        }

        // 构建
        MsgReq msgReq = MessageFactory.genMsgReqToGroup(BizContext.getTotalUsername(), data);
        logger.info("send msg group, msg is: [{}]", msgReq);

        // 发送群组消息
        channel.writeAndFlush(msgReq);
    }
}
