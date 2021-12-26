package com.shawnliang.github.netty.server.strategy;

import com.shawnliang.github.netty.core.dto.UserDataInfo;
import com.shawnliang.github.netty.core.message.MessageResp;
import com.shawnliang.github.netty.core.message.MessageRespBuilder;
import com.shawnliang.github.netty.core.message.MsgReq;
import com.shawnliang.github.netty.server.data.ClientRegisterInfo;
import com.shawnliang.github.netty.core.dto.UserChannelInfo;
import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.time.LocalDateTime;

/**
 * Description :  注册消息响应处理器 .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/19
 */
public class RegisterMsgProcessor implements IMsgProcessor{

    @Override
    public MessageResp process(MsgReq messageReq, Channel channel) {
        Object data = messageReq.getData();
        if (data == null) {
           return MessageRespBuilder.failure();
        }

        UserDataInfo userDataInfo = (UserDataInfo) data;
        LocalDateTime loginTime = userDataInfo.getLoginTime();
        String userName = userDataInfo.getUserName();

        UserChannelInfo userChannelInfo = new UserChannelInfo();
        userChannelInfo.setLoginTime(loginTime);

        // 保存用户到注册表中
        ClientRegisterInfo.saveUser(userName, userDataInfo);
        ClientRegisterInfo.saveChannel(userName, (NioSocketChannel) channel);
        ClientRegisterInfo.printAllRegister();

        // 返回注册表信息
        MessageResp resp = new MessageResp();
        resp.setSuccess(true);
        resp.setResult(ClientRegisterInfo.getClientRegistry());
        return resp;
    }
}
