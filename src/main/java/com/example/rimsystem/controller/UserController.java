package com.example.rimsystem.controller;

import com.example.rimsystem.bean.User;
import com.example.rimsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/doLogin")
    public String doLogin(User user, HttpSession session) {
        // 判断用户名密码是否正确
        User loginUser = userService.selectUserByName(user.getName(),user.getPassword());
        if (loginUser != null) {
            // 说明用户名密码正确，登录成功
            session.setAttribute("loginUser", loginUser);
            return "homePage";
        }

        return "login";
    }
}
