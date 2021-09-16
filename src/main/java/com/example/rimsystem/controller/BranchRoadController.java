package com.example.rimsystem.controller;

import com.example.rimsystem.bean.BranchRoad;
import com.example.rimsystem.service.BranchRoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @auther luyu
 */
@Controller
public class BranchRoadController {
    @Autowired
    BranchRoadService branchRoadService;
    @RequestMapping("/insertOneBranchRoad")
    public void insertOneBranchRoad(){
        BranchRoad branchRoad = new BranchRoad("下沙路A",1,200.0,1);
        branchRoadService.insertOneBranchRoad(branchRoad);
    }
}
