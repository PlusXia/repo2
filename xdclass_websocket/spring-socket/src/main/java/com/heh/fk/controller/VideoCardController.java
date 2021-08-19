package com.heh.fk.controller;

import com.heh.fk.mode.VideoCardDO;
import com.heh.fk.service.VideoCardService;
import com.heh.fk.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequestMapping("/api/v1/card")
@RestController
public class VideoCardController {

    @Autowired
    private VideoCardService videoCardService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 缓存key
     */
    private static final String VIDEO_CARD_CACHE_KEY = "video:card:key";

    /**
     * 有缓存
     * @return
     */
    @GetMapping("/list_cache")
    public JsonData listCardCache(){
        Object cacheObj = redisTemplate.opsForValue().get(VIDEO_CARD_CACHE_KEY);
        DataType type = redisTemplate.type(VIDEO_CARD_CACHE_KEY);
        if(cacheObj != null){
            List<VideoCardDO> list = (List<VideoCardDO>) cacheObj;
            JsonData jsonData = JsonData.buildSuccess(list);
            return jsonData;
        } else {
            List<VideoCardDO> list = videoCardService.list();
            redisTemplate.opsForValue().set(VIDEO_CARD_CACHE_KEY,list,10, TimeUnit.MINUTES);
            return JsonData.buildSuccess(list);
        }

    }


    /**
     * 无缓存
     * @return
     */
    @GetMapping("/list_nocache")
    public JsonData listCardNoCache(){


        List<VideoCardDO> list = videoCardService.list();

        return JsonData.buildSuccess(list);

    }


}
