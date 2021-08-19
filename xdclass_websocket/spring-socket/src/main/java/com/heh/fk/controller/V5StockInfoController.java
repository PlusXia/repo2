package com.heh.fk.controller;

import com.heh.fk.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@SuppressWarnings("all")
public class V5StockInfoController {

    @Autowired
    private WebSocketService ws;

    /*@Scheduled(fixedRate=1000)*/
    public void stockInfo(){
        ws.sendStockInfo();
    }
}
