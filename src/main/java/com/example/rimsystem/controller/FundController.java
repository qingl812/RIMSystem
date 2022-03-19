package com.example.rimsystem.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.rimsystem.bean.*;
import com.example.rimsystem.mapper.UserGeneralMapper;
import com.example.rimsystem.service.FundService;
import com.example.rimsystem.service.PatrolService;
import com.example.rimsystem.seucurity.Result;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther luyu
 */
@RestController
public class FundController {
    @Autowired
    private FundService fundService;
    @Autowired
    PatrolService patrolService;
    @Autowired
    UserGeneralMapper userGeneralMapper;
    @RequestMapping("/approveFund")
    public Result approveFund(@Param(value = "approveId")Integer approveId){
        fundService.approve(approveId);
        System.out.println(approveId);
        return Result.ok();
    }
    /**
     * 点击月度资金申请，出现所有的系统生成的内容
     */
    @RequestMapping("/monthFund")
    public Result monthFund(@RequestBody HashMap map){
        Integer planId = (Integer)map.get("planId");
        List<MonthFund> monthFunds = fundService.monthFundByPlanId(planId);
        return Result.ok().data("monthFunds",monthFunds);
    }
    /**
     * 点击申请以后，将所有信息保存至数据库
     */
    @RequestMapping("/insertMonthFunds")
    public Result insertMonthFund(@RequestBody JSONObject jsonObject){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String organization=null;
        if(principal instanceof UserDetails){
            AccountUser user = (AccountUser)principal;
            User user1 = new User();
            user.setUsername(user.getUsername());
            User one = userGeneralMapper.selectOne(user1);
            organization = one.getOrganizationName();
        }
        String year = (String) jsonObject.get("year");
        String month = (String) jsonObject.get("month");
        Integer planId = (Integer) jsonObject.get("planId");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        String refixTime = year+"-"+month;
        Date parse = null;
        try {
            parse = df.parse(refixTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String fundName = year+"年"+month+"月份市区道路维修计划";
        JSONArray tempMonthFunds = jsonObject.getJSONArray("monthFunds");
        List<MonthFund> monthFunds = tempMonthFunds.toJavaList(MonthFund.class);
        fundService.createFund(planId,organization,new Timestamp(parse.getTime()),new Timestamp(System.currentTimeMillis()),fundName,monthFunds);
        return Result.ok();
    }

    /**
     * @function:
     * @Param
     * @return
     */
    @RequestMapping("/searchMonthFund")
    public Result searchMonthFund(@RequestBody JSONObject jsonObject){
        Integer planId = jsonObject.getInteger("planId");
        Fund fund = fundService.searchMonthFund(planId);
        return Result.ok().data("fund",fund);
    }

    @RequestMapping("/searchYearFund")
    public Result searchYearFund(@RequestBody JSONObject jsonObject){
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        PageInfo pageInfo = fundService.searchYearFund(currentPage, pageSize);
        return Result.ok().data("pageInfo",pageInfo);
    }

    /**
     * @function 资金管理模块，资金支付管理中，点击某一年的支付查看，查看当年的所有月度资金表
     * @param jsonObject
     * @return
     */
    @RequestMapping("/searchMonthFundOfYear")
    public Result searchMonthFundOfYear(@RequestBody JSONObject jsonObject){
        Integer yearId = jsonObject.getInteger("yearId");
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        PageInfo<Fund> fundPageInfo = fundService.searchMonthFundOfYearFund(yearId, currentPage, pageSize);
        return Result.ok().data("pageInfo",fundPageInfo);
    }

    /**
     * @function 支付管理中，保存信息
     * @param fund
     * @return
     */
    @RequestMapping("/saveManagement")
    public Result saveManagement(@RequestBody Fund fund){
        fundService.saveManagement(fund);
        return Result.ok().message("保存成功");
    }

    /**
     *
     * @param jsonObject 需要的参数为currentPage当前页 pageSize 一页的数据量
     * @return
     */
    @RequestMapping("/searchAllYearFund")
    public Result searchAllYearFund(@RequestBody JSONObject jsonObject)
    {
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        PageInfo<YearFund> pageInfo = fundService.searchAllYearFund(currentPage, pageSize);
        return Result.ok().data("pageInfo",pageInfo);
    }

    @RequestMapping("/saveYearFund")
    public Result saveYearFund(@RequestBody JSONObject jsonObject){
        Integer year = jsonObject.getInteger("year");
        Double money = jsonObject.getDouble("money");
        fundService.saveYearFund(year,money);
        return Result.ok().message("保存成功");
    }

    @RequestMapping("/deleteYearFund")
    public Result deleteYearFund(@RequestBody JSONObject jsonObject){
        Integer yearFundId = jsonObject.getInteger("yearFundId");
        fundService.deleteYearFund(yearFundId);
        return Result.ok().message("删除成功");
    }

    @RequestMapping("/updateYearFund")
    public Result updateYearFund(@RequestBody JSONObject jsonObject){
        Integer yearFundId = jsonObject.getInteger("yearFundId");
        Double money = jsonObject.getDouble("money");
        fundService.updateYearFund(yearFundId,money);
        return Result.ok().message("更新成功");
    }
}
