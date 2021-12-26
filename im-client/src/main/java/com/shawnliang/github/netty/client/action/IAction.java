package com.shawnliang.github.netty.client.action;

/**
 * Description :  客户端行为 .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/26
 */
public interface IAction {

    /**
     * 点对点发送消息
     * @param to
     * @param data
     */
    void sendMsgToOne(String to, String data);

    /**
     * 群聊发送消息
     * @param data 消息内容
     */
    void sendMsgToGroup(String data);

}
