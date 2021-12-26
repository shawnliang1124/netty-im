package com.shawnliang.github.netty.core.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description :  消息类型枚举 .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/3
 */
@AllArgsConstructor
@Getter
public enum MsgTypeEnum {

    /**
     * 消息类型枚举
     */
    BIZ((byte) 1, "业务消息"),
    HEARTBEAT((byte) 2, "心跳消息"),
    REGISTER((byte)3, "注册消息"),
    ;

    byte value;

    String desc;

    public static MsgTypeEnum findByValue(byte key) {
        for (MsgTypeEnum value : MsgTypeEnum.values()) {
            if (key == value.value) {
                return value;
            }
        }

        return null;
    }


}
