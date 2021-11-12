package com.example.rimsystem.controller;

import com.example.rimsystem.bean.User;
import com.example.rimsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
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
//    @RequestMapping("/doLogin")
//    @ResponseBody
//    public int doLogin(@RequestBody User user, HttpSession session) {
//        // 判断用户名密码是否正确
//        User loginUser = userService.selectUserByName(user.getName(),user.getPassword());
//        if (loginUser != null) {
//            // 说明用户名密码正确，登录成功
//            session.setAttribute("loginUser", loginUser);
//            return 1;
//        }
//        return 0;
//    }

    @RequestMapping("/logOut")
    public String doLogOut(HttpSession session){
        session.setAttribute("loginUser",null);
        return "logIn";
    }
    @RequestMapping("/checkUser")
    @ResponseBody
    public User checkUser(HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        return loginUser;
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
