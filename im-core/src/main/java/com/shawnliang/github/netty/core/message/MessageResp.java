package com.shawnliang.github.netty.core.message;

import java.io.Serializable;
import lombok.Data;

/**
 * Description :  消息响应体 .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/1
 */
@Data
public class MessageResp implements Serializable {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 返回的结果
     */
    private Object result;

}
