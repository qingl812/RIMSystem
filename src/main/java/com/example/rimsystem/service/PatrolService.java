package com.example.rimsystem.service;

import com.example.rimsystem.bean.*;
import com.example.rimsystem.seucurity.Result;

import java.sql.Timestamp;
import java.util.List;

public interface PatrolService {
    PageBean<PatrolLog> searchOneRoadPatrol(Integer id,Integer currentPage);
    List<BranchPatrol> searchPatrolOfBranchRoad(Integer patrolId);
    int createOnePatrol(Integer roadId,Timestamp checkTime,String weather,String patrolPerson,String roadName);
    void insertBranchPatrol(BranchPatrol branchPatrol);
    PageBean<PatrolLog> searchPatrolByTime(Integer pageSize,Integer roadId, Integer currentPage, String startTime, String endTime);
    PageBean<Road> searchAllRoadPatrol(Road road,Integer currentPage);
    List<PatrolLog> searchPatrolByMonth(String startTime, String endTime);
    BranchPatrol searchDetail(Integer branchPatrolId);
    int createPlanTable(Integer[] branchPatrolId, String year, String month, String[] remarks);
    MaintenancePlan selectBranchPatrolsByPlan(Integer planId);
    void createTable(Table table);

    Result deleteSelectedPatrol(int patrolId);
}
