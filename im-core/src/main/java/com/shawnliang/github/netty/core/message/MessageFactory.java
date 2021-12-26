package com.shawnliang.github.netty.core.message;

import com.shawnliang.github.netty.core.constant.Constant;
import com.shawnliang.github.netty.core.dto.MsgGroupReq;
import com.shawnliang.github.netty.core.dto.MsgOneReq;
import com.shawnliang.github.netty.core.dto.UserDataInfo;
import java.time.LocalDateTime;

/**
 * Description : 消息工厂构造器  .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/3
 */
public final class MessageFactory {

    /**
     * 构造心跳消息
     * @return
     */
    public static MsgReq genHeartBeat() {
        MsgReq heartBeatMsgReq = new MsgReq();

        final Header header = new Header();
        header.setType(MsgTypeEnum.HEARTBEAT.value);
        heartBeatMsgReq.setHeader(header);

        heartBeatMsgReq.setData(System.currentTimeMillis());

        return heartBeatMsgReq;
    }

    /**
     * 生成注册的信息
     * @return
     */
    public static MsgReq genRegisterReq(String userName) {
        MsgReq heartBeatMsgReq = new MsgReq();


        Header header = new Header();
        header.setType(MsgTypeEnum.REGISTER.value);
        heartBeatMsgReq.setHeader(header);

        UserDataInfo userDataInfo = UserDataInfo.builder().userName(userName)
                .loginTime(LocalDateTime.now()).build();
        heartBeatMsgReq.setData(userDataInfo);

        return heartBeatMsgReq;
    }

    /**
     * 构建单聊消息请求体
     * @param to 发送对象用户名
     * @param data 消息体内容
     * @return
     */
    public static MsgReq genMsgReqToOne(String from, String to, String data) {
        MsgReq msgReq = new MsgReq();

        Header header = new Header();
        header.setType(MsgTypeEnum.BIZ.getValue());
        msgReq.setHeader(header);

        MsgOneReq msgOneReq = new MsgOneReq();
        msgOneReq.setFromUser(from);
        msgOneReq.setToUser(to);
        msgOneReq.setData(data);
        msgOneReq.setSendTime(LocalDateTime.now());

        msgReq.setData(msgOneReq);

        return msgReq;
    }

    /**
     * 构建群聊的信息请求体
     * @param from 信息来源
     * @param data 请求体
     * @return 群聊的信息请求体
     */
    public static MsgReq genMsgReqToGroup(String from, String data) {
        MsgReq msgReq = new MsgReq();

        Header header = new Header();
        header.setType(MsgTypeEnum.BIZ.getValue());
        msgReq.setHeader(header);

        MsgGroupReq msgGroupReq = new MsgGroupReq();
        msgGroupReq.setFromUser(from);
        msgGroupReq.setData(data);
        msgGroupReq.setSendTime(LocalDateTime.now());
        msgGroupReq.setGroupId(Constant.TOTAL_GROUP_ID);
        msgReq.setData(msgGroupReq);

        return msgReq;
    }

}
