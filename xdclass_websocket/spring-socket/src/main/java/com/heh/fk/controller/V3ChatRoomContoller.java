package com.heh.fk.controller;

import com.heh.fk.mode.InMessage;
import com.heh.fk.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@SuppressWarnings("ALL")
public class V3ChatRoomContoller {

    @Autowired
    private WebSocketService ws;

    @MessageMapping("/v3/single/chat")
    public void singleChat(InMessage message) {

        ws.sendChatMessage(message);

    }
}
