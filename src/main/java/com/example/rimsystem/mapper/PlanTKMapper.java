package com.example.rimsystem.mapper;

import com.example.rimsystem.bean.BranchPatrol;
import com.example.rimsystem.bean.MaintenancePlan;
import com.example.rimsystem.bean.Table;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PlanTKMapper extends Mapper<MaintenancePlan> {
    @Update("update maintenance_plan set is_act='是' where id=#{planId}")
    void updatePlanAct(Integer planId);
    List<MaintenancePlan> selectAllPlanLimit(int index, Integer pageSize);
    List<BranchPatrol> selectBranchPatrolsOfPlan(Integer planId);

}
