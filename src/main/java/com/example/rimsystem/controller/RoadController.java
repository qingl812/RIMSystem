package com.example.rimsystem.controller;

import com.example.rimsystem.bean.PageBean;
import com.example.rimsystem.bean.Road;
import com.example.rimsystem.mapper.RoadGeneralMapper;
import com.example.rimsystem.service.RoadService;
import lombok.AllArgsConstructor;
import lombok.Data;
<<<<<<< HEAD
import org.springframework.http.HttpRequest;
=======
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> ebba27af98024fb1cf7fe49c45ebb164a91734b2
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class RoadController {
    @Autowired
    RoadService roadService;
    @Autowired
    RoadGeneralMapper roadGeneralMapper;
    @RequestMapping("/updateCoordinate")
    @ResponseBody
    public String updateCoordinate(@RequestBody List<Coordinate> list) {
        String coordinate = "";
        for (Coordinate coordinate1 : list) {
            coordinate += (coordinate1.lng + ',');
            coordinate += (coordinate1.lat + '#');
        }
        System.out.println(coordinate);
        return "homePage";
    }

    @RequestMapping("/allRoadPages")
    @ResponseBody
    public PageBean<Road> allNotePages(String currentPage) {
        if (currentPage == null || "".endsWith(currentPage)) {
            currentPage = "1";
        }
        int curPage = Integer.parseInt(currentPage);

        PageBean<Road> pageBean = new PageBean<>();
        pageBean.setCurrentPage(curPage);
        List<Road> list = roadService.selectAllPages(pageBean);
        pageBean.setPageData(list);
        System.out.println(pageBean);
        return pageBean;
    }
    @RequestMapping("/hh")
    @ResponseBody
    public int hhh(){
        Road road = new Road();
        int i = roadGeneralMapper.selectCount(road);
        return i;
    }
    @Data
    @AllArgsConstructor
    static class Coordinate {
        private String lng;
        private String lat;
    }

}
