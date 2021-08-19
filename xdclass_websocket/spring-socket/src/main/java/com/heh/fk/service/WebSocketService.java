package com.heh.fk.service;

import com.heh.fk.controller.User;
import com.heh.fk.mode.InMessage;
import com.heh.fk.mode.OutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate template;

    public void sendTopicMessage(String dest, InMessage message) throws InterruptedException {
        template.convertAndSend(dest, new OutMessage(message.getContent()));
    }

    public void sendChatMessage(InMessage message) {
        template.convertAndSend("/chat/single/" + message.getTo(),
                new OutMessage(message.getFrom() + " 发送:" + message.getContent()));
    }

    public void sendServerInfo() {

        int processors = Runtime.getRuntime().availableProcessors();

        Long freeMem = Runtime.getRuntime().freeMemory();

        Long maxMem = Runtime.getRuntime().maxMemory();

        String message = String.format("服务器可用处理器:%s; 虚拟机空闲内容大小: %s; 最大内存大小: %s", processors, freeMem, maxMem);

        template.convertAndSend("/topic/server_info", new OutMessage(message));

    }

    public void sendStockInfo() {
        Map<String, String> stockInfoMap = StockService.getStockInfo();
        String msgTpl = "名称: %s ; 价格: %s元 ; 最高价: %s ; 最低价: %s ; 涨跌幅: %s ; 市盈率TTM: %s ; 总市值: %s";

        if (null != stockInfoMap) {
            String msg = String.format(msgTpl, stockInfoMap.get("prod_name"), stockInfoMap.get("last_px"), stockInfoMap.get("high_px"),
                    stockInfoMap.get("low_px"), stockInfoMap.get("px_change"), stockInfoMap.get("market_value"), stockInfoMap.get("amplitude"));

            template.convertAndSend("/topic/stock_info", new OutMessage(msg));
        }
    }

    /**
     * 功能描述：发送在线用户
     *
     * <p> 创建时间：Jan 6, 2018 </p>
     *
     * @since 0.0.1
     */
    public void sendOnlineUser(Map<String, User> onlineUser) {
        String msg = "";
        for (Map.Entry<String, User> entry : onlineUser.entrySet()) {
            msg = msg.concat(entry.getValue().getUsername() + " || ");
        }
        template.convertAndSend("/topic/onlineuser", new OutMessage(msg));
    }

    /**
     * 功能描述： v6: 用于多人聊天
     *
     * <p> 创建时间：Jan 6, 2018 </p>
     *
     * @since 0.0.1
     */
    public void sendTopicChat(InMessage message) {
        String msg = message.getFrom() + " 发送:" + message.getContent();
        template.convertAndSend("/topic/chat", new OutMessage(msg));
    }

}
