package com.example.rimsystem.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RoadController {
    @RequestMapping("/homePage")
    public String doHomePage(){
        return "homePage";
    }

    @RequestMapping("/updateCoordinate")
    @ResponseBody
    public String updateCoordinate(@RequestBody List<Coordinate> list){
        String coordinate="";
        for (Coordinate coordinate1 : list) {
            coordinate+=(coordinate1.lng+',');
            coordinate+=(coordinate1.lat+'#');
        }
        System.out.println(coordinate);
        return "homePage";
    }
    @Data
    @AllArgsConstructor
    static class Coordinate{
        private String lng;
        private String lat;
    }
}
