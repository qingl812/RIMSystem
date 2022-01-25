package com.example.rimsystem.controller;

import com.example.rimsystem.bean.Road;
import com.example.rimsystem.bean.RoadType;
import com.example.rimsystem.service.RoadTypeService;
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
    @RequestMapping("/searchType")
    public List<RoadType> searchRoadType(){
        List<RoadType> types = roadTypeService.searchRoadType();
        return types;
    }

}
