package com.shawnliang.github.netty.core.convert;

import com.shawnliang.github.netty.core.message.Header;
import com.shawnliang.github.netty.core.message.MsgReq;
import com.shawnliang.github.netty.core.message.MsgTypeEnum;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/19
 */
public class StringToMessageReqConvertor {

    /**
     * 将消息转换成业务消息体
     * @param msg
     * @return
     */
    public static MsgReq genBizMessageReq(String msg) {
        MsgReq msgReq =
                new MsgReq();

        msgReq.setData(msg);

        Header header = new Header();
        header.setType(MsgTypeEnum.BIZ.getValue());
        msgReq.setHeader(header);

        return msgReq;
    }

}
