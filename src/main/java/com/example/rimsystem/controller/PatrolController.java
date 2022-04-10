package com.example.rimsystem.controller;

import com.alibaba.fastjson.JSONArray;
import com.example.rimsystem.bean.*;
import com.example.rimsystem.service.PatrolService;
import com.example.rimsystem.seucurity.Result;
import com.example.rimsystem.seucurity.ResultCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @auther luyu
 */
@RestController
public class PatrolController {
//    静态常量定义存放的位置
    public final static String UPLOAD_PATH_PREFIX = "static/uploadPatrol/";
    @Autowired
    PatrolService patrolService;




//具体的新建一个呈批表，并和维修计划关联
    /**
     * 必须使用json格式，因为参数绑定时，使用了@JsonFormat("pattern='yyyy-MM-dd'")
     * @return ok
     */
    @RequestMapping("/createTable")
    public Result createTable(@RequestBody Table table){

        patrolService.createTable(table);
        return Result.ok();
    }

//    点击实施呈批表后，显示出来的项目概况
    @RequestMapping("/projectDetail")
    public Result showProjectDetails(Integer planId){
        MaintenancePlan plan = patrolService.selectBranchPatrolsByPlan(planId);
        List<BranchPatrol> branchPatrols = plan.getBranchPatrols();
        StringBuffer onePatrol = new StringBuffer("");
        int totalPavementBreak=0;
        int totalSidewalkBrickBreak=0;
        int totalBlindBrickBreak=0;
        int totalCurbBreak=0;
        for (BranchPatrol patrol : branchPatrols) {
            onePatrol.append(patrol.getPatrolLog().getRoadName()).append(",").append(patrol.getBranchRoad().getBranchName()).append(',').append("维修路面").append(patrol.getBranchRoad().getPavementBreak())
                    .append("m^2,").append("维修人行道").append(patrol.getBranchRoad().getSidewalkBrickBreak()).append("m^2,")
                    .append("维修盲道").append(patrol.getBranchRoad().getBlindBrickBreak()).append("m^2,")
                    .append("维修缘石").append(patrol.getBranchRoad().getCurbBreak()).append("m;").append("<br/>");
            totalPavementBreak+=patrol.getBranchRoad().getPavementBreak();
            totalSidewalkBrickBreak+=patrol.getBranchRoad().getSidewalkBrickBreak();
            totalBlindBrickBreak+=patrol.getBranchRoad().getBlindBrickBreak();
            totalCurbBreak+=patrol.getBranchRoad().getCurbBreak();
        }
        StringBuffer projectDetails = new StringBuffer();
        projectDetails.append("维修路面").append(totalPavementBreak).append("m^2,").append("维修人行道").append(totalSidewalkBrickBreak).append("m^2,")
                .append("维修盲道").append(totalBlindBrickBreak).append("m^2,").append("维修缘石").append(totalCurbBreak).append("m。").append("<br/>").append(onePatrol);
        Map map = new HashMap();
        map.put("projectDetails",projectDetails);
        map.put("planId",planId);
        return Result.ok().data(map);
    }

//  病害汇总，并选择具体的几条病害形成维修计划表
@RequestMapping("/diseaseSummary")
public Result  diseaseSummary(@RequestBody HashMap map){
    String month = (String) map.get("month");
    String year = (String) map.get("year");
    List<Integer> branchPatrolIdsTemp =  (List<Integer>) map.get("branchPatrolId");
    Integer[] branchPatrolIds = new Integer[branchPatrolIdsTemp.size()];
    branchPatrolIdsTemp.toArray(branchPatrolIds);
    List<String> remarksTemp = (List<String>) map.get("remarks");
    String[] remarks = new String[remarksTemp.size()];
    remarksTemp.toArray(remarks);
    if(branchPatrolIds.length!=branchPatrolIds.length){
        return Result.error(ResultCode.PARAM_NOT_COMPLETE);
    }
    int id = patrolService.createPlanTable(branchPatrolIds, year, month, remarks);
    return Result.ok().data("planId",id);
}

//    查看某一条具体的巡查信息
    @RequestMapping("/searchDetailOfPatrol")
    public Result searchDetailOfPatrol(Integer branchPatrolId){
        BranchPatrol branchPatrol = patrolService.searchDetail(branchPatrolId);
        return Result.ok().data("detail",branchPatrol);
    }

//    道路检测模块中的根据年月查找所有道路的所有巡查信息
    @RequestMapping("/searchPatrolByMonth")
    public Result searchPatrolByMonth(@RequestParam(required = false) String year,@RequestParam(required = false) String month){
            if(year.equals("")||year==null||month.equals("")||month==null){
                return Result.error(ResultCode.PARAM_IS_BLANK).message("年月不能为空");
            }
            else {
                String startDate = year + "/" + month + "/" + "1";
                Integer tempMonth = Integer.parseInt(month);
                tempMonth++;
                month = tempMonth.toString();
                String endDate = year + "/" + month + "/" + "1";
                List<PatrolLog> patrolLogs = patrolService.searchPatrolByMonth(startDate, endDate);
                return Result.ok().data("patrolLogs",patrolLogs);
            }
    }

//    道路检测模块,查找根据道路名字养护等级种类三个条件查找所有的道路
    @RequestMapping("/searchAllRoadPatrol")
    public Result searchAllRoadPatrol(Road road,Integer currentPage){
        PageBean<Road> roadPageBean = patrolService.searchAllRoadPatrol(road, currentPage);
        Map map = new HashMap();
        map.put("pageBean",roadPageBean);
        return Result.ok().data(map);
    }
//道路巡查模块中的  查找某一条路的所有巡查信息,与下面的方法,就少了几个条件

    /**
     * TODO:有空给这个方法删了,和下面重复了
     * @param roadId
     * @param currentPage
     * @return
     */
    @RequestMapping("/searchOneRoadPatrol")
    public Result searchOneRoadPatrolByRoadId(Integer roadId,Integer currentPage){
        PageBean<PatrolLog> pageBean = patrolService.searchOneRoadPatrol(roadId, currentPage);
        Map map = new HashMap();
        map.put("pageBean",pageBean);
        return Result.ok().data(map);
    }
//道路巡查模块中根据年月日区间查找某一条道路的所有巡查信息
    @RequestMapping("/searchPatrolByTime")
    public Result searchPatrolByTime(Integer pageSize,Integer roadId,Integer currentPage,String startTime,String endTime){
        PageBean<PatrolLog> pageBean = patrolService.searchPatrolByTime(pageSize,roadId, currentPage,startTime,endTime);
        Map map = new HashMap();
        map.put("pageBean",pageBean);
        return Result.ok().data(map);
    }
//道路查询模块中根据某一个查询日志,查询具体的支路的损坏情况
    @RequestMapping("/searchBranchPatrol")
    public Result searchBranchPatrol(Integer patrolId){
        List<BranchPatrol> branchPatrols = patrolService.searchPatrolOfBranchRoad(patrolId);
        Map patrols = new HashMap<>();
        patrols.put("branchPatrols", branchPatrols);
        return Result.ok().data(patrols);
    }

    @RequestMapping("/insertOnePatrol")
//    记得采用json格式输入数据
    public Result insertOnePatrol(@RequestBody PatrolLog patrolLog){
        int patrolId = patrolService.createOnePatrol(patrolLog.getRoadId(),patrolLog.getCheckTime(),patrolLog.getWeather(), patrolLog.getPatrolPerson(), patrolLog.getRoadName());
        return Result.ok().data("patrolId",patrolId);
    }

    @RequestMapping(value = "/insertBranchPatrol",headers = "content-type=multipart/form-data")
    public Result insertBranchPatrol(BranchPatrol branchPatrol, @RequestParam(required = false,value = "file") MultipartFile[] files){
        if(files==null){
            branchPatrol.setImagePath("");
            branchPatrol.setAccessoryPath("");
        }
        else{
            String realPath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + UPLOAD_PATH_PREFIX;
            for (MultipartFile file : files) {
                if(file.getOriginalFilename().endsWith("jpg")||file.getOriginalFilename().endsWith("jpeg")||
                        file.getOriginalFilename().endsWith("png")||file.getOriginalFilename().endsWith("bmp")
                ||file.getOriginalFilename().endsWith("tiff")){
                    System.out.println("进来了");
                    File fileDir = new File(realPath+branchPatrol.getBranchRoad().getBranchName()+"/"+"img"+"/");
                    if(!fileDir.isDirectory()){
                        fileDir.mkdirs();
                    }
                    String oldName = file.getOriginalFilename();
                    String newName = UUID.randomUUID().toString()+oldName.substring(oldName.lastIndexOf("."),oldName.length());
                    File newFile = new File(fileDir.getAbsolutePath()+File.separator+newName);
                    try {
                        file.transferTo(newFile);
                        branchPatrol.setImagePath(fileDir.getAbsolutePath()+File.separator+newName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    File fileDir = new File(realPath+branchPatrol.getBranchRoad().getBranchName()+"/"+"accessory"+"/");
                    if(!fileDir.isDirectory()){
                        fileDir.mkdirs();
                    }
                    String oldName = file.getOriginalFilename();
                    String newName = UUID.randomUUID().toString()+oldName.substring(oldName.lastIndexOf("."),oldName.length());
                    File newFile = new File(fileDir.getAbsolutePath()+File.separator+newName);
                    try {
                        file.transferTo(newFile);
                        branchPatrol.setAccessoryPath(fileDir.getAbsolutePath()+File.separator+newName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        patrolService.insertBranchPatrol(branchPatrol);
        return Result.ok();
    }
}
