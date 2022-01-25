package com.example.rimsystem.handler;

import com.example.rimsystem.seucurity.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther luyu
 */
@Component
public class MyLogOutHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if(authentication!=null){
            new SecurityContextLogoutHandler().logout(httpServletRequest,httpServletResponse,authentication);
        }
        Result out = Result.ok().message("退出登录");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setHeader("authentication","");
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(out));
    }
}
