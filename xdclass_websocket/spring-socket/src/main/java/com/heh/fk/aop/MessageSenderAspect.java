package com.heh.fk.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.heh.fk.annotation.SendMessage;
import com.heh.fk.constants.TopicConstants;
import com.heh.fk.mode.MessageAdapt;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.annotation.Resource;

@Aspect
@Configuration
public class MessageSenderAspect {

    @Resource
    private SimpMessagingTemplate template;

    @Autowired(required = false)
    private RedisTemplate redisTemplate;


    @AfterReturning(pointcut = "@annotation(sendMessage)", returning = "resp")
    public void afterReturning(SendMessage sendMessage, Object resp) {
        if (resp == null) {
            return;
        }
        JSONObject jobj = new JSONObject();
        if (resp instanceof String) {
            jobj.put("data", resp);
        } else {
            String str = JSON.toJSONStringWithDateFormat(resp, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
            jobj.put("data", JSONObject.parse(str));
        }

        if (redisTemplate == null) {
            template.convertAndSend(sendMessage.topic(), jobj);
        } else {
            MessageAdapt message = new MessageAdapt();
            message.setTopic(sendMessage.topic());
            message.setMessage(jobj);
            redisTemplate.convertAndSend(TopicConstants.REDIS_TOPIC, message);
        }
    }

}
