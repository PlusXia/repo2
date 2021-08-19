package com.heh.fk.mode;

import java.io.Serializable;

public class MessageAdapt implements Serializable {

    /**
     * 主题路径
     */
    private String topic;

    /**
     * 主题消息
     */
    private Object message;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

}
