package com.example.rimsystem.service;

import com.example.rimsystem.bean.Fund;
import com.example.rimsystem.bean.MonthFund;
import com.example.rimsystem.bean.YearFund;
import com.github.pagehelper.PageInfo;

import java.sql.Timestamp;
import java.util.List;

public interface FundService {
    void approve(Integer approveId);

    List<MonthFund> monthFundByPlanId(Integer planId);

    void createMonthFunds(List<MonthFund> monthFunds);

    void createFund(Integer planId, String organization, Timestamp timestamp, Timestamp timestamp1, String fundName, List<MonthFund> monthFunds);

    Fund searchMonthFund(Integer planId);

    PageInfo searchYearFund(Integer currentPage, Integer pageSize);

    PageInfo<Fund> searchMonthFundOfYearFund(Integer yearId,Integer currentPage, Integer pageSize);

    void saveManagement(Fund fund);

    PageInfo<YearFund> searchAllYearFund(Integer currentPage, Integer pageSize);

    void saveYearFund(Integer year, Double money);

    void deleteYearFund(Integer yearFundId);

    void updateYearFund(Integer yearFundId,Double money);
}
