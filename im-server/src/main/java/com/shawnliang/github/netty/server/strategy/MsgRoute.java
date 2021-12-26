package com.shawnliang.github.netty.server.strategy;

import com.shawnliang.github.netty.core.message.MessageResp;
import com.shawnliang.github.netty.core.message.MsgReq;
import com.shawnliang.github.netty.core.message.MsgTypeEnum;
import io.netty.channel.Channel;
import java.util.HashMap;
import java.util.Map;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/19
 */
public class MsgRoute {

    private static volatile Map<MsgTypeEnum, IMsgProcessor> DATA_MAP;


    /**
     * 根据消息体的类型来处理
     * @param req
     * @return
     */
    private static IMsgProcessor findByMsgReq(MsgReq req) {
        if (DATA_MAP == null) {
            synchronized (MsgRoute.class) {
                if (DATA_MAP == null) {
                    DATA_MAP = new HashMap<>();

                    // 初始化DATA_MAP
                    DATA_MAP.put(MsgTypeEnum.BIZ, new BizMsgProcessor());
                    DATA_MAP.put(MsgTypeEnum.HEARTBEAT, new DefaultMsgProcessor());
                    DATA_MAP.put(MsgTypeEnum.REGISTER, new RegisterMsgProcessor());
                }
            }
        }

        byte type = req.getHeader().getType();
        return DATA_MAP.get(MsgTypeEnum.findByValue(type));
    }

    /**
     * 处理消息体，并且得到响应体的对象
     * @param req
     * @return
     */
    public static MessageResp processor(MsgReq req, Channel channel) {
        IMsgProcessor processor = MsgRoute.findByMsgReq(req);
        if (processor != null) {
            return processor.process(req, channel);
        }

        return genDefaultResp();
    }

    /**
     * 生成默认的消息返回体
     * @return 生成默认的消息返回体
     */
    private static MessageResp genDefaultResp() {
        MessageResp messageResp = new MessageResp();
        messageResp.setSuccess(false);
        messageResp.setResult(null);

        return messageResp;
    }

}
