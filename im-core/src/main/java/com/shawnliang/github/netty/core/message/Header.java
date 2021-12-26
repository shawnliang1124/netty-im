package com.shawnliang.github.netty.core.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/1
 */
@Data
public class Header implements Serializable {

    /***
     * 消息类型
     *  1 代表业务消息
     *  2 代表心跳消息
     */
    private byte type;


    /**
     * 额外信息
     */
    private Map<String, Object> extraParams = new HashMap<>(2);

}
