package com.shawnliang.github.netty.server.data;

import com.google.common.collect.Lists;
import com.shawnliang.github.netty.core.dto.UserDataInfo;
import com.shawnliang.github.netty.core.util.CollectionUtil;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/19
 */
public class ClientRegisterInfo {

    private static final Logger logger = LogManager.getLogger(ClientRegisterInfo.class);

    private static final Map<String, UserDataInfo> CLIENT_REGISTRY;

    private static final Map<String, List<NioSocketChannel>> USER_CHANNEL_HOLDER;

    static {
        CLIENT_REGISTRY = new ConcurrentHashMap<>();
        USER_CHANNEL_HOLDER = new ConcurrentHashMap<>();
    }

    /**
     * 保存用户的连接信息
     * @param userName 用户名称
     * @param userDataInfo 用户信息
     */
    public static void saveUser(String userName, UserDataInfo userDataInfo) {
        CLIENT_REGISTRY.putIfAbsent(userName, userDataInfo);
    }

    /**
     * 保存用户的channel信息
     * @param userName
     * @param channel
     */
    public static void saveChannel(String userName, NioSocketChannel channel) {
        List<NioSocketChannel> nioSocketChannels = USER_CHANNEL_HOLDER.get(userName);
        if (!CollectionUtil.isEmpty(nioSocketChannels)) {
            nioSocketChannels.add(channel);
        } else {
            USER_CHANNEL_HOLDER.put(userName, Lists.newArrayList(channel));
        }
    }

    public static UserDataInfo getByUserName(String userName) {
        return CLIENT_REGISTRY.get(userName);
    }

    public static List<NioSocketChannel> getChannelByUserName(String userName) {
        return USER_CHANNEL_HOLDER.get(userName);
    }

    public static void remove(String userName) {
        CLIENT_REGISTRY.remove(userName);
    }

    /**
     * 删除channel 列表
     * @param channel
     */
    public static void removeChannel(NioSocketChannel channel) {
        if (channel != null) {
            for (List<NioSocketChannel> nioSocketChannels : USER_CHANNEL_HOLDER.values()) {
                nioSocketChannels.removeIf(registerChannel -> {
                    boolean removed = registerChannel.equals(channel);
                    if (removed) {
                        logger.info("remove channel already, channel: {}", channel);
                    }
                    return removed;

                });
            }
        }
    }

    /**
     * 获取client的所有信息
     * @return
     */
    public static Map<String, UserDataInfo> getClientRegistry() {
        return CLIENT_REGISTRY;
    }

    /**
     * 获取所有的channel -> 用于群发
     * @return
     */
    public static Map<String, List<NioSocketChannel>> getUserChannelHolder() {
        return USER_CHANNEL_HOLDER;
    }

    public static void printAllRegister() {
        logger.info("CLIENT_REGISTRY: {}", CLIENT_REGISTRY);
        logger.info("USER_CHANNEL_HOLDER: {}", USER_CHANNEL_HOLDER);
    }

}
