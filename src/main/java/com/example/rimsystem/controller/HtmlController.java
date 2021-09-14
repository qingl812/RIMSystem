package com.example.rimsystem.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HtmlController {
    @RequestMapping("/homePage")
    public String doHomePage() {
        return "homePage";
    }
}
