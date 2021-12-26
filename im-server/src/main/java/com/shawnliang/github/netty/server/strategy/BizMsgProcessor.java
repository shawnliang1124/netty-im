package com.shawnliang.github.netty.server.strategy;

import com.shawnliang.github.netty.core.constant.Constant;
import com.shawnliang.github.netty.core.dto.MsgGroupReq;
import com.shawnliang.github.netty.core.dto.MsgOneReq;
import com.shawnliang.github.netty.core.message.MessageResp;
import com.shawnliang.github.netty.core.message.MessageRespBuilder;
import com.shawnliang.github.netty.core.message.MsgReq;
import com.shawnliang.github.netty.core.util.CollectionUtil;
import com.shawnliang.github.netty.server.data.ClientRegisterInfo;
import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Description :   单聊\群聊消息解析类.
 *
 * @author : Phoebe
 * @date : Created in 2021/12/19
 */
public class BizMsgProcessor implements IMsgProcessor{

    @Override
    public MessageResp process(MsgReq messageReq, Channel channel) {
        Object data = messageReq.getData();
        if (data instanceof MsgOneReq) {
           return doWithMsgOne((MsgOneReq) data);
        }
        else if (data instanceof MsgGroupReq) {
            return doWithMsgGroup((MsgGroupReq) data);
        }

        return MessageRespBuilder.failure();
    }

    /**
     * 处理群发消息
     * @param msgGroupReq 群发消息对象
     * @return
     */
    private MessageResp doWithMsgGroup(MsgGroupReq msgGroupReq) {
        if (Objects.equals(Constant.TOTAL_GROUP_ID, msgGroupReq.getGroupId())) {
            Map<String, List<NioSocketChannel>> userChannelHolder = ClientRegisterInfo
                    .getUserChannelHolder();

            // 循环注册的channel去群发信息
            userChannelHolder.forEach((userName, channels) -> {
                if (!Objects.equals(userName, msgGroupReq.getFromUser())) {
                    for (NioSocketChannel channel : channels) {
                        // 群体发送消息
                        MessageResp groupResp = MessageRespBuilder.success(msgGroupReq);
                        channel.writeAndFlush(groupResp);
                    }
                }
            });

            // return 群发消息成功的响应给发送者
            return MessageRespBuilder.success();
        }

        // TODO 暂时所有的client都在同一个群组中
        return MessageRespBuilder.failure();
    }

    /**
     * 处理单聊消息
     * @param msgOneReq 单聊消息
     * @return
     */
    private MessageResp doWithMsgOne(MsgOneReq msgOneReq) {
        // 获得单聊发送的信息
        String toUser = msgOneReq.getToUser();

        List<NioSocketChannel> targetChannelList = ClientRegisterInfo
                .getChannelByUserName(toUser);
        if (!CollectionUtil.isEmpty(targetChannelList)) {
            // 构建发送消息的详情内容
            for (NioSocketChannel nioSocketChannel : targetChannelList) {
                MessageResp resp = MessageRespBuilder.success(msgOneReq);
                nioSocketChannel.writeAndFlush(resp);
            }

            // 返回发送成功
            return MessageRespBuilder.success();
        }

        return MessageRespBuilder.failure();
    }
}
