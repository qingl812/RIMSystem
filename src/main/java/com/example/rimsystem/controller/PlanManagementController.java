package com.example.rimsystem.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.rimsystem.bean.*;
import com.example.rimsystem.service.PlanService;
import com.example.rimsystem.service.RoadService;
import com.example.rimsystem.service.UserService;
import com.example.rimsystem.seucurity.Result;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @auther luyu
 */
@RestController
public class PlanManagementController {
    @Autowired
    PlanService planService;
    @Autowired
    UserService userService;
    @Autowired
    RoadService roadService;

    @RequestMapping("/allManagement")
    public Result allManagement(){
        List<String> strings = userService.selectAllManagement();
        return Result.ok().data("Organization",strings);
    }

    @RequestMapping("/selectHomePageRoad")
    public Result selectHomePageRoad(@RequestBody JSONObject jsonObject){
        String unit = jsonObject.getString("unit");
        String roadName = jsonObject.getString("roadName");
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        PageInfo<Road> roadPageInfo = roadService.selectHomePageRoad(unit,roadName,currentPage,pageSize);
        return Result.ok().data("pageInfo",roadPageInfo);
    }

    /**
     * @function：进去维修管理模块后，显示所有的维修计划,资金管理资金月度申请第一个界面同样调用此方法
     * @param jsonObject 参数值为currentPage当前页和pageSize一页显示的数据数
     * @return：所有的维修计划
     */
    @RequestMapping("/allPlan")
    public Result allPlan(@RequestBody JSONObject jsonObject){
        Integer currentPage = (Integer)jsonObject.get("currentPage");
        Integer pageSize = (Integer)jsonObject.get("pageSize");
        PageBean pageBean = planService.selectAllPlan(currentPage, pageSize);
        return Result.ok().data("pageBean",pageBean);
    }
    /**
     * @function：点击查看某一个具体的维修计划,在维修管理模块中，维修计划和现场实施记录使用同一个接口
     * @param jsonObject 参数值为维修计划的id值
     * @return：具体的一个维修计划
     */
    @RequestMapping("/patrolOfPlan")
    public Result patrolOfPlan(@RequestBody JSONObject jsonObject){
        Integer planId = (Integer) jsonObject.get("planId");
        List<BranchPatrol> branchPatrols = planService.searchPatrolOfPlan(planId);
        return Result.ok().data("patrols",branchPatrols);
    }
    /**
     * @function 查看所有的呈批表
     * @param jsonObject 参数值为currentPage当前页和pageSize一页显示的数据数,和年月(格式必须为yyyy/MM)
     * @return 所有的呈批表
     * @TODO 还得把实施是否执行，是否完成加进去
     */
    @RequestMapping("/AllTable")
    public Result searchAllTable(@RequestBody JSONObject jsonObject){
        String createTableTime = (String) jsonObject.get("createTableTime");
        Integer currentPage = (Integer) jsonObject.get("currentPage");
        Integer pageSize = (Integer) jsonObject.get("pageSize");
        PageInfo pageInfo = planService.searchAllTable(currentPage, pageSize, createTableTime);
        return Result.ok().data("pageInfo",pageInfo);
    }

    /**
     * @function：点击查看某一个具体的维修计划的呈批表
     * @param jsonObject 参数值为维修计划的id值
     * @return：具体的一个维修计划
     */
    @RequestMapping("/selectOneTable")
    public Result selectOntTable(@RequestBody JSONObject jsonObject){
        Integer id = (Integer)jsonObject.get("id");
        Table table = planService.selectOneTable(id);
        return Result.ok().data("table",table);
    }
    @RequestMapping("/deleteOneTable")
    public Result deleteOneTable(@RequestBody JSONObject jsonObject){
        Integer id = (Integer)jsonObject.get("id");
        planService.deleteOneTable(id);
        return Result.ok().message("删除成功");
    }
    /**
     * @function：道路维修模块中，现场实施记录中，查看某一个plan的呈批表，同一个接口可以使用在支付查看和支付管理查看呈批表中
     * @param jsonObject 参数值为维修计划的planId值
     * @return：具体的一个
     */
    @RequestMapping("/selectTableByPlanId")
    public Result selectTableByPlanId(@RequestBody JSONObject jsonObject)
    {
        Integer planId = (Integer) jsonObject.get("planId");
        Table table = planService.selectTableByPlanId(planId);
        return Result.ok().data("table",table);
    }

    /**
     * @TODO 和前端沟通好传入的参数和限制，在进行文件的保存
     * @param files
     * @param jsonObject
     * @return
     */
    @RequestMapping("/saveBranchOfPlan")
    public Result saveBranchPatrolOfPlan(List<MultipartFile> files, String jsonObject ){
        JSONObject tempJsonObject = JSONObject.parseObject(jsonObject);
        JSONArray id = tempJsonObject.getJSONArray("id");
        JSONArray finishTime = tempJsonObject.getJSONArray("finishTime");
        List<Integer> idList = id.toJavaList(Integer.class);
        List<String> stringList = finishTime.toJavaList(String.class);
        return Result.ok();
    }
}
