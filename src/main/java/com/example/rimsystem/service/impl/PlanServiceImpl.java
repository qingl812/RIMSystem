package com.example.rimsystem.service.impl;

import com.example.rimsystem.bean.*;
import com.example.rimsystem.mapper.PatrolTKMapper;
import com.example.rimsystem.mapper.PlanTKMapper;
import com.example.rimsystem.mapper.TableTKMapper;
import com.example.rimsystem.service.PlanService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @auther luyu
 */
@Service
public class PlanServiceImpl implements PlanService {
    @Autowired
    PlanTKMapper planTKMapper;
    @Autowired
    TableTKMapper tableTKMapper;
    @Autowired
    PatrolTKMapper patrolTKMapper;

    @Override
    public Table selectTableByPlanId(Integer planId) {
        Table table = new Table();
        table.setPlanId(planId);
        return tableTKMapper.selectOne(table);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOneTable(Integer id) {
        Table table = new Table();
        table.setId(id);
        tableTKMapper.delete(table);
    }

    @Override
    public Table selectOneTable(Integer id) {
        Table table = new Table();
        table.setId(id);
        Table selectOne = tableTKMapper.selectOne(table);
        return selectOne;
    }

    @Override
    public List<BranchPatrol> searchPatrolOfPlan(Integer planId) {
        return planTKMapper.selectBranchPatrolsOfPlan(planId);
    }

    @Override
    public PageInfo<Table> searchAllTable(Integer currentPage, Integer pageSize, String timestamp)  {
        Example example = new Example(Table.class);
        Example.Criteria criteria = example.createCriteria();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM");
        Date start = null;
        try {
            start = df.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.MONTH,1);
        Date end = calendar.getTime();
        criteria.andBetween("createTableTime",new Timestamp(start.getTime()),new Timestamp(end.getTime()));
        PageHelper.startPage(currentPage,pageSize);
        List<Table> tables = tableTKMapper.selectByExample(example);
        PageInfo<Table> pageInfo = new PageInfo<>(tables);
        return pageInfo;
    }

    @Override
    public List<MaintenancePlan> selectAllPlan(Integer currentPage,Integer pageSize) {
        PageBean<MaintenancePlan> pageBean = new PageBean<>();
        pageBean.setTotalCount(planTKMapper.selectCount(new MaintenancePlan()));
        pageBean.setCurrentPage(currentPage);
        if (currentPage == 0) {
            pageBean.setCurrentPage(1);
        }
        else if(currentPage == (pageBean.getTotalPage() + 1)) {
            pageBean.setCurrentPage(pageBean.getTotalPage());
        }
        int index = (pageBean.getCurrentPage() - 1) * pageSize;
        return planTKMapper.selectAllPlanLimit(index, pageSize);
    }
}
