package com.example.rimsystem.controller;

import com.example.rimsystem.bean.PageBean;
import com.example.rimsystem.bean.Road;
import com.example.rimsystem.mapper.RoadGeneralMapper;
import com.example.rimsystem.service.RoadService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RoadController {
    @Autowired
    RoadService roadService;
    @Autowired
    RoadGeneralMapper roadGeneralMapper;
//    根据条件查询某条道路
    @RequestMapping("/selectOneRoad")
    @ResponseBody
    public PageBean<Road> selectOneRoadByInf(@RequestParam(value = "currentPage") Integer currentPage,String roadName,String roadType,String roadMaintenance)
    {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(currentPage);
        List list = roadService.selectOneRoadByInfo(roadName, roadType, roadMaintenance, pageBean);
        pageBean.setPageData(list);
        return pageBean;
    }
//    删除某一条道路，包括删除他的子道路以及他的文档信息
    @RequestMapping("/deleteRoad")
    @ResponseBody
    public int deleteRoadById(@RequestBody Integer roadId){
        return roadService.deleteRoadById(roadId);
    }
//    点击某一条道路，在地图上绘制出他的路线
    @RequestMapping("/drawLine")
    @ResponseBody
    public List<Coordinate> drawRoadLineBySearchId(@RequestBody Integer roadId){
        String coordinateTemp = roadService.selectCoordinateById(roadId);
        List<Coordinate> coordinates = new ArrayList<>();
        if(coordinateTemp!=null&&coordinateTemp!="") {
            String[] split = coordinateTemp.split("#");
            for (String s : split) {
                if (s != "") {
                    int index = s.indexOf(",");
                    Coordinate coordinate = new Coordinate(s.substring(0, index), s.substring(index + 1));
                    coordinates.add(coordinate);
                }
            }
        }
        else {
            return null;
        }
        return coordinates;
    }
//    用来保存地图上绘制的坐标点
    @RequestMapping("/updateCoordinate")
    @ResponseBody
    public void updateCoordinate(@RequestBody List<Coordinate> list) {
        String coordinate = "";
        Integer roadId = 3;
        for (Coordinate coordinate1 : list) {
            coordinate += (coordinate1.lng + ',');
            coordinate += (coordinate1.lat + '#');
        }
        roadService.updateCoordinateByRoadId(roadId,coordinate);
    }
//前端发送过来页数，后端进行分页，并将数据传递向前端
    @RequestMapping("/allRoadPages")
    @ResponseBody
    public PageBean<Road> allNotePages(@RequestBody Integer currentPage) {
        if (currentPage == null) {
            currentPage = 1;
        }
        PageBean<Road> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        List<Road> list = roadService.selectAllPages(pageBean);
        pageBean.setPageData(list);
        return pageBean;
    }
//   一个内部类，用来存储坐标
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    static class Coordinate {
        private String lng;
        private String lat;
    }

}
