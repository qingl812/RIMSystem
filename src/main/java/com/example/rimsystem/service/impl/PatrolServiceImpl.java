package com.example.rimsystem.service.impl;

import com.example.rimsystem.bean.*;
import com.example.rimsystem.mapper.*;
import com.example.rimsystem.service.PatrolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @auther luyu
 */
@Service
public class PatrolServiceImpl implements PatrolService {
    @Autowired
    PatrolTKMapper patrolTKMapper;
    @Autowired
    RoadTKMapper roadTKMapper;
    @Autowired
    RoadMapper roadMapper;
    @Autowired
    PlanTKMapper planTKMapper;
    @Autowired
    TableTKMapper tableTKMapper;

    @Override
    public MaintenancePlan selectBranchPatrolsByPlan(Integer planId) {
        return patrolTKMapper.selectBranchPatrolsByPlan(planId);
    }

    @Override
    public void createTable(Table table) {
        planTKMapper.updatePlanAct(table.getPlanId());
        tableTKMapper.insert(table);
    }

    @Override
    public BranchPatrol searchDetail(Integer branchPatrolId) {
        return patrolTKMapper.selectDetail(branchPatrolId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = "Exception.class")
    public int createPlanTable(Integer[] branchPatrolIds, String year, String month, String[] remarks) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        String tempTime = year+"-"+month;
        Date date =null;
        try {
            date = df.parse(tempTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        MaintenancePlan plan = new MaintenancePlan(null,new Timestamp(date.getTime()),null,"否","否");
        planTKMapper.insert(plan);
        for (int i = 0; i < branchPatrolIds.length; i++) {
            int branchPatrolId = branchPatrolIds[i];
            String remark = remarks[i];
            patrolTKMapper.insertPlanAndPatrolRealtion(plan.getId(),branchPatrolId,remark);
        }
        return plan.getId();

    }

    @Override
    public List<PatrolLog> searchPatrolByMonth(String startTime, String endTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date start = null;
        try {
            start = df.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date end = null;
        try {
            end = df.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<PatrolLog> patrolLogs = patrolTKMapper.selectPatrolsAndBranchsByRoadId(new Timestamp(start.getTime()), new Timestamp(end.getTime()));
        return patrolLogs;
    }

    @Override
//    道路检测模块进去后，显示的所有道路信息
    public PageBean<Road> searchAllRoadPatrol(Road road,Integer currentPage) {
            PageBean<Road> pageBean = new PageBean<>();
            pageBean.setTotalCount(roadTKMapper.selectCount(road));
            pageBean.setCurrentPage(currentPage);
            if (pageBean.getCurrentPage() == 0) {
                pageBean.setCurrentPage(1);
            } else if (pageBean.getCurrentPage() == (pageBean.getTotalPage() + 1)) {
                pageBean.setCurrentPage(pageBean.getTotalPage());
            }
            int count = pageBean.getPageCount();
            int index = (pageBean.getCurrentPage() - 1) * count;
            pageBean.setPageData(roadMapper.selectAllRoad(road.getRoadName(), road.getRoadType(), road.getRoadMaintenanceGrade(), index, count));
        return pageBean;
    }
    @Override
//    查找某一条道路的所有巡查消息
    public PageBean<PatrolLog> searchOneRoadPatrol(Integer id,Integer currentPage) {
        PageBean<PatrolLog> pageBean = new PageBean<>();
        pageBean.setTotalCount(patrolTKMapper.selectCount(new PatrolLog().setRoadId(id)));
        pageBean.setCurrentPage(currentPage);
        if (pageBean.getCurrentPage() == 0) {
            pageBean.setCurrentPage(1);
        }
        else if(pageBean.getCurrentPage() == (pageBean.getTotalPage() + 1)) {
            pageBean.setCurrentPage(pageBean.getTotalPage());
        }
        int count = pageBean.getPageCount();
        int index = (pageBean.getCurrentPage() - 1) * count;
        pageBean.setPageData(patrolTKMapper.selectPatrolsByRoadId(id,index,count));
        return pageBean;

    }
//查找一条巡查记录中的所有消息
    @Override
    public List<BranchPatrol> searchPatrolOfBranchRoad(Integer patrolId) {
        return  patrolTKMapper.selectPatrolOfBranchRoad(patrolId);
    }
//创建一条新的查询记录
    @Override
    public int createOnePatrol(Integer roadId,Timestamp checkTime ,String weather, String patrolPerson,String roadName) {
        PatrolLog patrolLog = new PatrolLog(null,checkTime,weather,patrolPerson,roadId,roadName);
        patrolTKMapper.insert(patrolLog);
        return patrolLog.getId();
    }

    @Override
    public void insertBranchPatrol( BranchPatrol branchPatrol) {
        patrolTKMapper.insertBranchPatrol(branchPatrol);
    }

    @Override
    public PageBean<PatrolLog> searchPatrolByTime(Integer roadId, Integer currentPage, String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date start = null;
        Date end = null;
        try {
            start = sdf.parse(startTime);
            end = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PageBean<PatrolLog> pageBean = new PageBean<>();
        Example example = new Example(PatrolLog.class);
        example.createCriteria().andBetween("checkTime",new Timestamp(start.getTime()),new Timestamp(end.getTime())).andEqualTo("roadId",roadId);
        pageBean.setTotalCount(patrolTKMapper.selectCountByExample(example));
        pageBean.setCurrentPage(currentPage);
        if (pageBean.getCurrentPage() == 0) {
            pageBean.setCurrentPage(1);
        }
        else if(pageBean.getCurrentPage() == (pageBean.getTotalPage() + 1)) {
            pageBean.setCurrentPage(pageBean.getTotalPage());
        }
        int count = pageBean.getPageCount();
        int index = (pageBean.getCurrentPage() - 1) * count;
        pageBean.setPageData(patrolTKMapper.searchPatrolByTime(roadId,currentPage,new Timestamp(start.getTime()),new Timestamp(end.getTime()),index,count));
        return pageBean;
    }
}
