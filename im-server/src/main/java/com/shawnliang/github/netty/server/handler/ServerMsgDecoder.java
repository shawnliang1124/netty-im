package com.shawnliang.github.netty.server.handler;

import com.shawnliang.github.netty.core.message.MsgReq;
import com.shawnliang.github.netty.core.util.ProtostuffUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * Description :  消息解码器 .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/1
 */
public class ServerMsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
            throws Exception {
        byte[] data = new byte[in.readableBytes()];
        // 将Bytebuf字节流的内容读到data 字节数组中
        in.readBytes(data);

        out.add(ProtostuffUtils.deserialize(data, MsgReq.class));
    }
}
