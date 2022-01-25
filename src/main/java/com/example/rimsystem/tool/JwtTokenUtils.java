package com.example.rimsystem.tool;

import com.example.rimsystem.handler.MyAuthenticationFailureHandler;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *@auther luyu
 */
@Component
public class JwtTokenUtils {
    @Autowired
    MyAuthenticationFailureHandler failureHandler;
    private  static String key="luyuhsa"; //密钥加盐
    private static long ttl=1000*60*60*24;   //过期时间为1天

    /**
     * 生成JWT
     */
    public  String createJWT(String username) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key);
        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();
    }
    /*
    解析jwt
     */
    public Claims JWTParse(String jwt){
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return null;
        }
    }

}
