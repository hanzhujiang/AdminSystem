package com.alanhub.vueadmin.controller;

import cn.hutool.core.map.MapUtil;
import com.alanhub.vueadmin.common.lang.Const;
import com.alanhub.vueadmin.common.lang.Result;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class AuthController extends BaseController{
    @Autowired
    Producer producer;

    @GetMapping("/captcha")
    public Result captcha() throws IOException {

        String key= UUID.randomUUID().toString();
        String code=producer.createText();

        BufferedImage image=producer.createImage(code);
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        ImageIO.write(image,"jpg",outputStream);

        BASE64Encoder encoder =new BASE64Encoder();
        String str="data:image/jpeg;base64,";

        String base64Img=str+encoder.encode(outputStream.toByteArray());

        redisUtil.hset(Const.CAPTCHA_KEY,key,code,120);

        return Result.succ(
                MapUtil.builder()
                        .put("token",key)
                        .put("captchaImg",base64Img)
                        .build()
        );

    }
}
