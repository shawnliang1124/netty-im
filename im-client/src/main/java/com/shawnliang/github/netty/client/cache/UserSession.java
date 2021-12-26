package com.shawnliang.github.netty.client.cache;

import com.shawnliang.github.netty.core.dto.UserDataInfo;
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
public class UserSession {

    private static final Logger logger = LogManager.getLogger(UserSession.class);


    private static Map<String, UserDataInfo> userSession;

    static {
        userSession = new ConcurrentHashMap<>();
    }

    /**
     * 保存用户的连接信息
     * @param userName
     * @param userDataInfo
     */
    public static void saveUser(String userName, UserDataInfo userDataInfo) {
        userSession.putIfAbsent(userName, userDataInfo);
    }

    public static UserDataInfo getByUserName(String userName) {
        return userSession.get(userName);
    }

    public static void remove(String userName) {
        userSession.remove(userName);
    }

    /**
     * 打印client端所有的注册表
     */
    public static void printAll() {
        userSession.forEach((k ,v)-> {
            logger.info("key is: {}, value is: {}", k, v);
        });
    }

}
