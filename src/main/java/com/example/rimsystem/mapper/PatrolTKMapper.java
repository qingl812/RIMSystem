package com.example.rimsystem.mapper;

import com.example.rimsystem.bean.BranchPatrol;
import com.example.rimsystem.bean.MaintenancePlan;
import com.example.rimsystem.bean.PatrolLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
public interface PatrolTKMapper extends Mapper<PatrolLog> {
    List<PatrolLog> selectPatrolsByRoadId(Integer roadId,Integer index,Integer count);
    List<BranchPatrol> selectPatrolOfBranchRoad(Integer patrolId);
    void insertBranchPatrol(BranchPatrol branchPatrol);
    List<PatrolLog> searchPatrolByTime(Integer roadId, Integer currentPage, Timestamp timestamp, Timestamp timestamp1,Integer index,Integer count);
    List<PatrolLog> selectPatrolsAndBranchsByRoadId(Timestamp startTime, Timestamp endTime);
    BranchPatrol selectDetail(Integer branchPatrolId);
    void insertPlanAndPatrolRealtion(Integer id, int branchPatrolId,String remark);
    MaintenancePlan selectBranchPatrolsByPlan(Integer planId);
    PatrolLog selectOnePlansPatrol(Integer planId);
    void deleteBranchWithPatrolLog(int patrolId);
}
