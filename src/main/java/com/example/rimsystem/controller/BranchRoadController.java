package com.example.rimsystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.rimsystem.bean.BranchRoad;
import com.example.rimsystem.service.BranchRoadService;
import com.example.rimsystem.seucurity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @auther luyu
 */
@RestController
public class BranchRoadController {
    @Autowired
    BranchRoadService branchRoadService;

    @RequestMapping("/insertOneBranchRoad")
    @ResponseBody
    public void insertOneBranchRoad(BranchRoad branchRoad){
        branchRoadService.insertOneBranchRoad(branchRoad);
    }

    @RequestMapping("/showImageType")
    public Result showImageType(){
        List<String> imageTypes = branchRoadService.showImageType();
        return Result.ok().data("imageTypes",imageTypes);
    }

    @RequestMapping("/insertBranchImage")
    public Result insertBranchImage(List<MultipartFile> files, @RequestBody JSONObject jsonObject){

        return Result.ok();

    }
}
