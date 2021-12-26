package com.shawnliang.github.netty.core.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Description : 业务信息  .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class MsgReq {

    /**
     * 消息头
     */
    private Header header;

    private Object data;

}
