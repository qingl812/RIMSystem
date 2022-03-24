package com.example.rimsystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.rimsystem.bean.PageBean;
import com.example.rimsystem.bean.Road;
import com.example.rimsystem.mapper.RoadTKMapper;
import com.example.rimsystem.service.RoadService;
import com.example.rimsystem.seucurity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

@RestController
@Api(tags = "关于主路的api")
public class RoadController {
    @Autowired
    RoadService roadService;
    @Autowired
    RoadTKMapper roadTKMapper;

    @ApiOperation(value = "用于保存某一条支路的信息，见图1.4基本信息1")
    @RequestMapping(value = "/updateRoadDetail",method = RequestMethod.POST)
    public Result updateRoadDetail(@ApiParam(name = "road" ,value = "需要的为road中的属性，必须要的值为id",required = true) @RequestBody Road road){
        roadService.updateRoadDetail(road);
        return Result.ok().message("保存成功");
    }
    @ApiOperation(value = "点击某一条主路后它的具体信息")
    @RequestMapping(value = "/selectRoadDetail",method = RequestMethod.POST)
    public Result selectRoadDetail(@ApiParam(name = "jsonObject",value = "需要的值为roadId",required = true)@RequestBody JSONObject jsonObject){
        Integer roadId = jsonObject.getInteger("roadId");
        Road road = roadService.selectRoadDetail(roadId);
        return Result.ok().data("road",road);
    }

//    根据条件查询某条道路
    @RequestMapping("/api/road_info_list")
    @ResponseBody
    public PageBean<Road> selectOneRoadByInf(@RequestBody HashMap map)
    {
        Integer currentPage = (Integer) map.get("currentPage");
        String roadName = (String) map.get("roadName");
        String roadType = (String) map.get("roadType");
        String roadMaintenance = (String) map.get("roadMaintenance");
        Integer pageSize = (Integer) map.get("pageSize");
        System.out.println(currentPage);
        System.out.println(roadMaintenance);
        System.out.println(roadType);
        System.out.println(roadName);
        System.out.println(pageSize);
        PageBean pageBean = new PageBean();
        pageBean.setPageCount(5);
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
//前端发送过来页数，后端进行分页，并将数据传递向前端,功能为查询一页上所有的数据
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
