package com.example.rimsystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.rimsystem.bean.BranchRoad;
import com.example.rimsystem.service.BranchRoadService;
import com.example.rimsystem.service.RoadService;
import com.example.rimsystem.seucurity.Result;
import com.example.rimsystem.seucurity.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @auther luyu
 */
@Api(value = "关于支路的api")
@RestController
public class BranchRoadController {
    @Autowired
    BranchRoadService branchRoadService;
    @Autowired
    RoadService roadService;

    @RequestMapping("/insertOneBranchRoad")
    @ResponseBody
    public void insertOneBranchRoad(BranchRoad branchRoad){
        branchRoadService.insertOneBranchRoad(branchRoad);
    }

    @ApiOperation(value = "插入支路和主路照片时的照片类型选项")
    @RequestMapping("/showImageType")
    public Result showImageType(){
        List<String> imageTypes = branchRoadService.showImageType();
        return Result.ok().data("imageTypes",imageTypes);
    }
    @ApiOperation(value = "插入支路或者主路的图片使用的都是这个接口")
    @RequestMapping("/insertBranchImage")
    public Result insertBranchImage(@RequestPart(name = "file") @ApiParam (name = "file", value = "传入的图片（一张）",required = true) MultipartFile file,@RequestPart(name = "zipFile") @ApiParam (name = "zipFile", value = "传入的压缩图片",required = true) MultipartFile zipFile, @ApiParam(name = "roadPicture",value = "详见model中的picture以及road的id")@RequestPart(value = "roadPicture") JSONObject jsonObject){
        if (file.isEmpty()){
            return Result.error(ResultCode.PARAM_IS_BLANK).message("上传的图片不能为空");
        }
        Integer roadId = jsonObject.getInteger("roadId");
        Integer branchId = jsonObject.getInteger("branchId");
        String pictureName = jsonObject.getString("pictureName");
        String pictureType = jsonObject.getString("pictureType");
        String remark = jsonObject.getString("remark");
        jsonObject.getInteger("branch");
        if(pictureName==null||pictureType==null){
            return Result.error(ResultCode.PARAM_IS_BLANK).message("照片名称或照片类型不能为空");
        }
        if (roadId==null){
//            branchRoadService.insertPicture();
        }
        else if(branchId==null){
//            roadService.insertPicture();
        }
        return Result.ok();

    }
}
