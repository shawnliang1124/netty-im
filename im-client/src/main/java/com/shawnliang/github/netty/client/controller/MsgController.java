package com.shawnliang.github.netty.client.controller;

import com.shawnliang.github.netty.client.init.ClientInitialize;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description :  发送消息入口 .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/26
 */
@RestController
@RequestMapping("/msg")
public class MsgController {

    @Autowired
    private ClientInitialize clientInitialize;

    @GetMapping("sendOne")
    public String sendMsgToOne(@PathParam("to") String to,
            @PathParam("msg") String msg) {
        clientInitialize.writeMsg(to, msg);
        return "success";
    }

    @GetMapping("sendGroup")
    public String sendMsgToGroup(@PathParam("msg") String msg) {
        clientInitialize.writeGroupMsg(msg);
        return "success";
    }

}
