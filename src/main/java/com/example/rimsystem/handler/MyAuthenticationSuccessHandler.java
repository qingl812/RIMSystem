package com.example.rimsystem.handler;

import com.example.rimsystem.seucurity.Result;
import com.example.rimsystem.tool.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther luyu
 */
//登录成功处理器
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    JwtTokenUtils jwtTokenUtils;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Result result = Result.ok().message("登录成功");
//        生成jwt并将它放在请求头里面
        String jwt = jwtTokenUtils.createJWT(authentication.getName());
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setHeader("authentication",jwt);
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
}
