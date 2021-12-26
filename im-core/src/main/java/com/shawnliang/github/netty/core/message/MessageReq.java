package com.shawnliang.github.netty.core.message;

import java.io.Serializable;
import lombok.Data;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/1
 */
@Data
public class MessageReq implements Serializable {


    /**
     * 消息头
     */
    private Header header;

}
