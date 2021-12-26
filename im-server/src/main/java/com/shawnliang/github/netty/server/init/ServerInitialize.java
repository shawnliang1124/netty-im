package com.shawnliang.github.netty.server.init;

import com.shawnliang.github.netty.server.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Description :   初始化配置类.
 *
 * @author : Phoebe
 * @date : Created in 2021/12/1
 */
public class ServerInitialize {

    private static final Logger logger = LogManager.getLogger(ServerInitialize.class);

    private static final NioEventLoopGroup bossGroup;

    private static final NioEventLoopGroup workerGroup;

    private static final ServerBootstrap serverBootstrap;

    static {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
    }

    private ServerInitialize() {
    }

    public static void start() {
        try {
            initServer();

            startServer();
        } catch (InterruptedException e) {
            logger.error("e is ", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private static void startServer() throws InterruptedException {
        System.out.println("start server success");
        final ChannelFuture sync =
                serverBootstrap.bind(7071).sync();

        sync.channel().closeFuture().sync();
    }

    private static void initServer() {
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 65536)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ServerHandler());
    }

}
