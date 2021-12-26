package com.shawnliang.github.netty.client.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/26
 */
public class BizContext {

    private static ThreadLocal<Map<String, Object>> BIZ_CONTEXT;

    public static volatile String TOTAL_USERNAME;

    static {
        Map<String, Object> BIZ_CONTAIN = new ConcurrentHashMap<>();
        BIZ_CONTEXT = new ThreadLocal<>();

        BIZ_CONTEXT.set(BIZ_CONTAIN);
    }

    /**
     * 设置用户名
     * @param username
     */
    public static void setTotalUsername(String username) {
        TOTAL_USERNAME = username;
    }

    /**
     * 获得用户名
     * @return
     */
    public static String getTotalUsername() {
        return TOTAL_USERNAME;
    }

    /**
     * 保存上下文
     * @param key 关键字
     * @param value 内容
     */
    public static void put(String key, String value) {
        Map<String, Object> stringObjectMap = BIZ_CONTEXT.get();
        stringObjectMap.put(key, value);
    }

    /**
     * 获得Key对应的value
     * @param key 关键字
     * @return
     */
    public static <T> T get(String key) {
        Map<String, Object> stringObjectMap = BIZ_CONTEXT.get();
        return (T) stringObjectMap.get(key);
    }


}
