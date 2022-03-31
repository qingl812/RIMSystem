package com.example.rimsystem.controller;

import com.example.rimsystem.annotation.Log;
import com.example.rimsystem.bean.Road;
import com.example.rimsystem.bean.RoadType;
import com.example.rimsystem.service.RoadTypeService;
import com.example.rimsystem.seucurity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther luyu
 */
@RestController
@RequestMapping("/roadType")
public class RoadTypeController {
    @Autowired
    RoadTypeService roadTypeService;
    @Log("搜索道路类型")
    @RequestMapping("/searchType")
    public List<RoadType> searchRoadType(){
        List<RoadType> types = roadTypeService.searchRoadType();
        return types;
    }

    @RequestMapping("/searchMaintenanceGrade")
    public Result searchMaintenanceGrade(){
        List<String> list = roadTypeService.searchMaintenanceGrade();
        return Result.ok().data("list",list);
    }
}
