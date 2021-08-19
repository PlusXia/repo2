package com.heh.fk.controller;

import com.heh.fk.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@SuppressWarnings("all")
public class V4ServerInfoController {

    @Autowired
    private WebSocketService ws;


    @MessageMapping("/v4/schedule/push")
    @Scheduled(fixedRate = 3000)  //方法不能加参数
    public void sendServerInfo(){
        ws.sendServerInfo();
    }

}
