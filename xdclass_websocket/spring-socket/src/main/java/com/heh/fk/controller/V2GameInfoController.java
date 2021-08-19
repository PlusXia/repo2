package com.heh.fk.controller;

import com.heh.fk.mode.InMessage;
import com.heh.fk.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@SuppressWarnings("ALL")
public class V2GameInfoController {

    @Autowired
    private WebSocketService ws;

    @MessageMapping("/v2/chat")
    public void gameInfo(InMessage message) throws InterruptedException {
        ws.sendTopicMessage("/topic/game_rank", message);
    }

}
