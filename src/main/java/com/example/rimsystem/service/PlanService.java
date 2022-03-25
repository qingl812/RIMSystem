package com.example.rimsystem.service;

import com.example.rimsystem.bean.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PlanService {
    PageBean selectAllPlan(Integer currentPage, Integer pageSize);

    List<BranchPatrol> searchPatrolOfPlan(Integer planId);

    PageInfo searchAllTable(Integer currentPage, Integer pageSize, String timestamp);

    Table selectOneTable(Integer id);

    void deleteOneTable(Integer id);

    Table selectTableByPlanId(Integer planId);
}
