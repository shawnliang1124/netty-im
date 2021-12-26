package com.shawnliang.github.netty.server;

import com.shawnliang.github.netty.core.message.Header;
import com.shawnliang.github.netty.core.message.MessageReq;
import com.shawnliang.github.netty.server.handler.ServerMsgDecoder;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/1
 */
public class ServerMsgDecoderTest {


    @Test
    public void testDecoder() {
        EmbeddedChannel channel = new EmbeddedChannel(new ServerMsgDecoder());

        MessageReq messageReq = new MessageReq();
        Header header = new Header();
        header.setType((byte) '1');
        header.getExtraParams().put("test", "value");
        messageReq.setHeader(header);

        channel.writeInbound(messageReq);
        boolean finish = channel.finish();
        System.out.println(finish);

        Object o = channel.readInbound();
    }

}
