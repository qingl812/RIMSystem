package com.example.rimsystem.controller;

import com.example.rimsystem.bean.BranchRoad;
import com.example.rimsystem.service.BranchRoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @auther luyu
 */
@Controller
public class BranchRoadController {
    @Autowired
    BranchRoadService branchRoadService;
    @RequestMapping("/insertOneBranchRoad")
    @ResponseBody
    public void insertOneBranchRoad(BranchRoad branchRoad){
        branchRoadService.insertOneBranchRoad(branchRoad);
    }
}
