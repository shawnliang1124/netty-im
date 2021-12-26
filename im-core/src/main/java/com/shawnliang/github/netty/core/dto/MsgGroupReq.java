package com.shawnliang.github.netty.core.dto;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/26
 */
@Data
public class MsgGroupReq {

    /**
     * 消息来源
     */
    private String fromUser;

    /**
     * 群组消息
     */
    private String groupId;

    /**
     * 消息内容
     */
    private String data;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;


}
