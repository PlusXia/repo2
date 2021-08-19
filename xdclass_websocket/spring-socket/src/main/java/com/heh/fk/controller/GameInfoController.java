package com.heh.fk.controller;

import com.heh.fk.annotation.SendMessage;
import com.heh.fk.mode.InMessage;
import com.heh.fk.mode.OutMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@SuppressWarnings("ALL")
public class GameInfoController {

    @MessageMapping("/v1/chat")
    @SendTo("/topic/game_chat")
    @SendMessage
    public OutMessage gameInfo(InMessage message) {
        return new OutMessage(message.getContent());
    }

}
