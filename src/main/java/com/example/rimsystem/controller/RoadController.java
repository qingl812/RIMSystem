package com.example.rimsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @auther luyu
 */
@Controller
@RequestMapping("/road")
public class RoadController {
    @RequestMapping("/dosth")
    public String dosth(){
        return "homePage";
    }
}
