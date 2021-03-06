package com.example.rimsystem.service.impl;

import com.example.rimsystem.bean.*;
import com.example.rimsystem.mapper.FundTKMapper;
import com.example.rimsystem.mapper.YearFundMapper;
import com.example.rimsystem.service.FundService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @auther luyu
 */
@Service
public class FundServiceImpl implements FundService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FundTKMapper fundTKMapper;

    @Autowired
    private YearFundMapper yearFundMapper;

    @Override
    public void updateYearFund(Integer yearFundId,Double money) {
        yearFundMapper.updateYearFundMoney(yearFundId,money);
    }

    @Override
    public void deleteYearFund(Integer yearFundId) {
        YearFund yearFund = new YearFund();
        yearFund.setId(yearFundId);
        yearFundMapper.delete(yearFund);
    }

    @Override
    public void saveYearFund(Integer year, Double money) {
        YearFund yearFund = new YearFund();
        yearFund.setYear(year);
        yearFund.setMoney(money);
        yearFund.setHadGiveMoney(new Double(0));
        yearFundMapper.insert(yearFund);
    }

    @Override
    public PageInfo<YearFund> searchAllYearFund(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<YearFund> select = yearFundMapper.select(new YearFund());
        PageInfo<YearFund> pageInfo = new PageInfo<>(select);
        return pageInfo;
    }

    @Override
    public void saveManagement(Fund fund) {
        fundTKMapper.saveManagement(fund);
    }

    @Override
    public PageInfo searchMonthFundOfYearFund(Integer id, Integer currentPage, Integer pageSize) {
        Fund fund = new Fund();
        fund.setYearFundId(id);
        PageHelper.startPage(currentPage,pageSize);
        List<Fund> select = fundTKMapper.select(fund);
        PageInfo<Fund> pageInfo = new PageInfo<>(select);
        return pageInfo;
    }

    @Override
    public PageInfo searchYearFund(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        YearFund yearFund = new YearFund();
        List<YearFund> select = yearFundMapper.select(yearFund);
        PageInfo<YearFund> pageInfo = new PageInfo<>();
        pageInfo.setList(select);
        return pageInfo;
    }

    @Override
    public Fund searchMonthFund(Integer planId) {
        return fundTKMapper.selectFundByPlanId(planId);
    }

    @Value("${approve.direct.exchange}")
    private String exchangeName;
    private String routeKey = "emailKey";
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void approve(Integer approveId) {
//        ???????????????????????????????????????????????????????????????
        System.out.println("?????????????????????????????????????????????");
//        ???????????????????????????????????????
        BranchRoad branchRoad = new BranchRoad();
        branchRoad.setBranchName("?????????A");
        rabbitTemplate.convertAndSend(exchangeName,routeKey,branchRoad.toString());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void createFund(Integer planId, String organization, Timestamp timestamp, Timestamp timestamp1, String fundName, List<MonthFund> monthFunds) {
        Fund fund = new Fund();
        fund.setPlanId(planId);
        fund.setOrganization(organization);
        fund.setRefixTime(timestamp);
        fund.setFundTime(timestamp1);
        fund.setFundName(fundName);
        Date date = new Date(timestamp1.getTime());
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        int year = instance.get(Calendar.YEAR);
//        ?????????????????????????????????
        YearFund yearFund = new YearFund();
        yearFund.setYear(year);
        YearFund tempYearFund = yearFundMapper.selectOne(yearFund);
        Double needToGivePrice = tempYearFund.getNoGiveMoney();
        if(yearFund==null){
            yearFundMapper.insert(yearFund);
        }

        double totalPrice = 0;
        fund.setYearFundId(tempYearFund.getId());
        fundTKMapper.insert(fund);
        for (MonthFund monthFund : monthFunds) {
            monthFund.setFundId(fund.getId());
            totalPrice+=monthFund.getUnitTotalPrice();
        }
        needToGivePrice +=totalPrice;
        createMonthFunds(monthFunds);
//        ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        fundTKMapper.updateTotalPrice(totalPrice,fund.getId());
        fundTKMapper.updatePlanTotalPrice(totalPrice,planId);

        fundTKMapper.updateYearFundNoGiveMoney(needToGivePrice,tempYearFund.getId());
    }

    @Override
    public List<MonthFund> monthFundByPlanId(Integer planId) {
        List<BranchPatrol> branchPatrols = fundTKMapper.selectMonthFundByPlanId(planId);
        List<MonthFund> monthFunds = new ArrayList<>();

        for (BranchPatrol branchPatrol : branchPatrols) {
            MonthFund monthFund = new MonthFund();
            MonthFund monthFund2 = new MonthFund();
            MonthFund monthFund3 = new MonthFund();
            MonthFund monthFund4 = new MonthFund();
//            ?????????????????????
            monthFund.setProjectName("????????????");
            monthFund.setProjectType(branchPatrol.getBranchRoad().getBranchPavementType());
            monthFund.setProjectContent("??????????????????");
            monthFund.setUnit("m^2");
            monthFund.setNumber(branchPatrol.getBranchRoad().getPavementBreak());
            monthFunds.add(monthFund);
//          ?????????????????????
            monthFund2.setProjectName("???????????????");
            monthFund2.setProjectType(branchPatrol.getBranchRoad().getBranchSidewalkBrickType());
            monthFund2.setProjectContent("??????????????????");
            monthFund2.setUnit("m^2");
            monthFund2.setNumber(branchPatrol.getBranchRoad().getSidewalkBrickBreak());
            monthFunds.add(monthFund2);
//          ??????????????????
            monthFund3.setProjectName("????????????");
            monthFund3.setProjectType(branchPatrol.getBranchRoad().getBranchBlindBrickType());
            monthFund3.setProjectContent("??????????????????");
            monthFund3.setUnit("m^2");
            monthFund3.setNumber(branchPatrol.getBranchRoad().getBlindBrickBreak());
            monthFunds.add(monthFund3);
//          ?????????????????????
            monthFund4.setProjectName("???????????????");
            monthFund4.setProjectType(branchPatrol.getBranchRoad().getBranchCurbType());
            monthFund4.setProjectContent("??????????????????");
            monthFund4.setUnit("m");
            monthFund4.setNumber(branchPatrol.getBranchRoad().getCurbBreak());
            monthFunds.add(monthFund4);
        }
        return monthFunds;

    }

    @Override
    public void createMonthFunds(List<MonthFund> monthFunds) {
        fundTKMapper.insertList(monthFunds);
    }
}
