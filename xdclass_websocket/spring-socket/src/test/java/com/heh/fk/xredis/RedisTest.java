package com.heh.fk.xredis;

import com.heh.fk.Application;
import com.heh.fk.mode.UserDo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testStringSet() {

        //ValueOperations valueOperations = redisTemplate.opsForValue();
        redisTemplate.opsForValue().set("name", "xdclass.net");

        stringRedisTemplate.opsForValue().set("str_name","xdclass.net+str_name");


    }


    @Test
    public void testStringGet() {

        //ValueOperations valueOperations = redisTemplate.opsForValue();

        String str1 = (String) redisTemplate.opsForValue().get("name");
        System.out.println(str1);

        String str2 = stringRedisTemplate.opsForValue().get("name");
        System.out.println(str2);

    }


    @Test
    public void testSeria() {

        UserDo userDO = new UserDo();
        userDO.setId(1);
        userDO.setName("二当家小D");
        userDO.setPwd("123");

        redisTemplate.opsForValue().set("user-service:user:2", userDO);

    }

}
