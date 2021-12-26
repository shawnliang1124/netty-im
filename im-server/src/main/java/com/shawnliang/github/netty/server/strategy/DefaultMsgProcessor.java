package com.shawnliang.github.netty.server.strategy;

import com.shawnliang.github.netty.core.message.MessageResp;
import com.shawnliang.github.netty.core.message.MsgReq;
import io.netty.channel.Channel;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/19
 */
public class DefaultMsgProcessor implements IMsgProcessor{

    @Override
    public MessageResp process(MsgReq messageReq, Channel channel) {
        return null;
    }
}
