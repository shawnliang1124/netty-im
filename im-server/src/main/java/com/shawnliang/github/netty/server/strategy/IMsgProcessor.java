package com.shawnliang.github.netty.server.strategy;

import com.shawnliang.github.netty.core.message.MessageResp;
import com.shawnliang.github.netty.core.message.MsgReq;
import io.netty.channel.Channel;

/**
 * Description : 消息处理器  .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/19
 */
public interface IMsgProcessor {

    /**
     * 处理请求消息
     * @param messageReq 请求消息体
     * @return 响应对象
     */
    MessageResp process(MsgReq messageReq, Channel channel);
}
