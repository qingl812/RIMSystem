package com.example.rimsystem.service;

import com.example.rimsystem.bean.BranchPatrol;
import com.example.rimsystem.bean.MaintenancePlan;
import com.example.rimsystem.bean.PatrolLog;
import com.example.rimsystem.bean.Table;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PlanService {
    List<MaintenancePlan> selectAllPlan(Integer currentPage,Integer pageSize);

    List<BranchPatrol> searchPatrolOfPlan(Integer planId);

    PageInfo searchAllTable(Integer currentPage, Integer pageSize, String timestamp);

    Table selectOneTable(Integer id);

    void deleteOneTable(Integer id);

    Table selectTableByPlanId(Integer planId);
}
