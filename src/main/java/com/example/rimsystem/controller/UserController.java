package com.example.rimsystem.controller;

import com.example.rimsystem.bean.AccountUser;
import com.example.rimsystem.bean.User;
import com.example.rimsystem.service.UserService;
import com.example.rimsystem.seucurity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/userInfo")
    public Result selectUserInfo(){
        SecurityContext context = SecurityContextHolder.getContext();
        AccountUser accountUser = (AccountUser)context.getAuthentication().getPrincipal();
        String username = accountUser.getUsername();
        User user = userService.selectUserInfoByUserName(username);
        return Result.ok().data("user",user);
    }

    @RequestMapping("/login/error")
    public void loginError(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        AuthenticationException exception =
                (AuthenticationException)request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        try {
            response.getWriter().write(exception.toString());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
