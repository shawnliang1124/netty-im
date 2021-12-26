package com.shawnliang.github.netty.core.dto;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * Description :  单聊消息 .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/26
 */
@Data
public class MsgOneReq {

    /**
     * 消息来源
     */
    private String fromUser;

    /**
     * 目标对象
     */
    private String toUser;

    /**
     * 消息内容
     */
    private String data;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;
}
