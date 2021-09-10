package com.example.rimsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/road")
public class RoadController {
    @RequestMapping("/homePage")
    public String doInformation(){

        //return "homeDir/main";
        return "homePage";
    }

}
