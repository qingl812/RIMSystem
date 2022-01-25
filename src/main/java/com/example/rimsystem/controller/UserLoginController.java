package com.example.rimsystem.controller;

import com.example.rimsystem.bean.User;
import com.example.rimsystem.service.UserService;
import com.example.rimsystem.seucurity.Result;
import com.example.rimsystem.seucurity.ResultCode;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @auther luyu
 */
@RestController
public class UserLoginController  {
    @Autowired
    UserService userService;
    @Autowired
    Producer producer;
    @Autowired
    RedisTemplate<String,String> redisTemplate;

//    将验证码图片和key值发送到前端
    @GetMapping("/captcha")
    public Result captcha() throws IOException {
        String key = UUID.randomUUID().toString();
        String code = producer.createText();
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image,"jpg",outputStream);
        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";
        String base64Img = str+encoder.encode(outputStream.toByteArray());
        Map map = new HashMap();
        redisTemplate.opsForHash().put("captcha",key,code);
        redisTemplate.expire("captcha",120, TimeUnit.SECONDS);
        map.put("capthcaImg",base64Img);
        map.put("key",key);
        return Result.ok().data(map);
    }
}
