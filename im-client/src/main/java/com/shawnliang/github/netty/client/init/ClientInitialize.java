package com.shawnliang.github.netty.client.init;

import com.shawnliang.github.netty.client.action.IAction;
import com.shawnliang.github.netty.client.action.impl.IActionImpl;
import com.shawnliang.github.netty.client.cache.UserSession;
import com.shawnliang.github.netty.client.context.BizContext;
import com.shawnliang.github.netty.client.handler.ClientHandler;
import com.shawnliang.github.netty.core.dto.UserDataInfo;
import com.shawnliang.github.netty.core.message.MessageFactory;
import com.shawnliang.github.netty.core.message.MsgReq;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.time.LocalDateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/3
 */
public class ClientInitialize {

    private final static NioEventLoopGroup group;

    private final static Bootstrap bootstrap;

    private static SocketChannel channel;

    private static IAction ACTION;

    private static final Logger logger = LogManager.getLogger(ClientInitialize.class);

    static {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
    }


    /**
     * 私有化构造函数，防止初始
     */
    public ClientInitialize(String host, int port, String userName) {
        // 预处理对象
        preSaveDetail(userName);

        // 初始化资源连接
        initClient();

        // 连接服务器
        connect(host, port);

        // 向服务端发送信息，拿到其他的客户端的信息
        registerToServer(userName);
    }

    /**
     * 预处理信息
     * @param userName 用户名
     */
    private void preSaveDetail(String userName) {
        BizContext.setTotalUsername(userName);
    }

    /**
     * 向服务端注册，并且拿到其他client的信息
     * @param userName
     */
    private void registerToServer(String userName) {
        MsgReq registerReq = MessageFactory.genRegisterReq(userName);

        // 发送注册消息，Inboud处理返回的信息
        channel.writeAndFlush(registerReq);
    }

    /**
     * 保存客户端信息
     * @param userName
     */
    private void saveUserDetail(String userName) {
        UserSession.saveUser(userName,
                UserDataInfo.builder()
                        .userName(userName)
                        .loginTime(LocalDateTime.now())
                        .build());
    }


    private static void initClient() {
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ClientHandler());
    }


    /**
     * 客户端连接
     */
    private static void connect(String host, int port) {
        try {
            // 发起异步的连接操作
            ChannelFuture f = bootstrap.connect(host, port).sync();
            // 如果client连接成功
            if (f.isSuccess()) {
                logger.info("client 连接成功");
                channel = (SocketChannel) f.channel();
                ACTION = new IActionImpl(channel);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
          //  release();
        }

    }

    private static void release() {
        group.shutdownGracefully();
    }

    /**
     * 发送个人信息 （私聊）
     * @param msg
     */
    public void writeMsg(String to, String msg) {
        ACTION.sendMsgToOne(to, msg);
    }

    /**
     * 发送群组消息
     * @param msg
     */
    public void writeGroupMsg(String msg) {
        ACTION.sendMsgToGroup(msg);
    }


}
