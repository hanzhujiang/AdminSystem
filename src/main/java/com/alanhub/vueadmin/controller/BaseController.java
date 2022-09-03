package com.alanhub.vueadmin.controller;



import javax.servlet.http.HttpServletRequest;
import com.alanhub.vueadmin.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired
    HttpServletRequest req;

    @Autowired
    RedisUtil redisUtil;
}
