package com.heh.fk.controller;

import com.google.code.kaptcha.Producer;
import com.heh.fk.mode.UserModel;
import com.heh.fk.mode.UserModel2;
import com.heh.fk.mode.UserModel3;
import com.heh.fk.util.CommonUtil;
import com.heh.fk.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/captcha")
@SuppressWarnings("all")
public class CaptchaController {


    @Autowired
    private StringRedisTemplate redisTemplate;


    @Autowired
    private Producer captchaProducer;

    @Autowired
    private UserModel userModel;

    @Autowired
    private UserModel3 userModel3;

    @Resource
    @Qualifier("primaryDataSource")
    private UserModel2 primaryDataSource;


    @GetMapping("get_captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("-------------------------------------------------------------");
        System.out.println(userModel);
        System.out.println(primaryDataSource);
        System.out.println(userModel3);
        System.out.println("-------------------------------------------------------------");
        String captchaText = captchaProducer.createText();

        String key = getCaptchaKey(request);

        //10分钟过期
        redisTemplate.opsForValue().set(key, captchaText, 10, TimeUnit.MINUTES);

        BufferedImage bufferedImage = captchaProducer.createImage(captchaText);

        ServletOutputStream outputStream = null;

        try {
            outputStream = response.getOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 发送验证码
     *
     * @return
     */
    @GetMapping("send_code")
    public JsonData sendCode(@RequestParam(value = "to", required = true) String to,
                             @RequestParam(value = "captcha", required = true) String captcha,
                             HttpServletRequest request) {
        String key = getCaptchaKey(request);
        String cacheCaptcha = redisTemplate.opsForValue().get(key);
        if (captcha != null && cacheCaptcha != null && cacheCaptcha.equalsIgnoreCase(captcha)) {
            redisTemplate.delete(key);
            //TODO 发送验证码
            return JsonData.buildSuccess();
        } else {
            return JsonData.buildError("验证码错误");
        }
    }


    private String getCaptchaKey(HttpServletRequest request) {
        String ip = CommonUtil.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");
        String key = "user-service:captcha:" + CommonUtil.MD5(ip + userAgent);
        return key;
    }

}
