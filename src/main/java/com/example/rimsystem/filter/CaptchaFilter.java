package com.example.rimsystem.filter;

import com.example.rimsystem.exception.CapthcaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @auther luyu
 */
public class CaptchaFilter extends OncePerRequestFilter {
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String url = httpServletRequest.getRequestURI();
        if(url.equals("/login")&&httpServletRequest.getMethod().equals("POST")){
//            校验验证码
            validate(httpServletRequest);
//            如果不正确，跳转到认证失败处理器
        }
    }
    public void validate(HttpServletRequest httpServletRequest){
        String code = httpServletRequest.getParameter("code");
        String key = httpServletRequest.getParameter("key");
        if(Objects.isNull(code)||Objects.isNull(key)){
            throw new CapthcaException("验证码错误");
        }
        if(!code.equals(redisTemplate.opsForHash().get("captcha",key))){
            throw new CapthcaException("验证码错误");
        }
        redisTemplate.opsForHash().delete("captcha",key);
    }
}
