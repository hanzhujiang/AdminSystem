package com.alanhub.vueadmin.utils;

import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;


@Data //添加getter setter
@Component //注入spring容器
@ConfigurationProperties(prefix = "alanhub.jwt")  //获取 application.properties 或 application.yml参数值
public class JwtUtils {
    private long expire;
    private String secret;
    private String header;


    //生成jwt
    public String generateToken(String username){
        Date nowDate=new Date();
        Date expireDate=new Date(nowDate.getTime()+1000*expire); //expire写到配置文件
       return Jwts.builder()
               .setHeaderParam("typ", "JWT")
               .setSubject(username)
               .setIssuedAt(nowDate)
               .setExpiration(expireDate)// 7天過期
               .signWith(SignatureAlgorithm.HS512, secret)
               .compact();
    }
    //解析jwt
    public Claims getClaimByToken(String jwt){
        try {
            return   Jwts.parser()
                      .setSigningKey(secret)
                      .parseClaimsJws(jwt)
                      .getBody();
        } catch (ExpiredJwtException e) {
            return null;
        }
    }

    //jwt是否过期
    public boolean isTokenExpired(Claims claims){
        return claims.getExpiration().before(new Date());
    }
}
