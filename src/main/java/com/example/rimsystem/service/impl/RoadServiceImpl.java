package com.example.rimsystem.service.impl;

import com.example.rimsystem.bean.BranchRoad;
import com.example.rimsystem.bean.PageBean;
import com.example.rimsystem.bean.Road;
import com.example.rimsystem.bean.RoadDoc;
import com.example.rimsystem.mapper.BranchRoadGeneralMapper;
import com.example.rimsystem.mapper.RoadDocGeneralMapper;
import com.example.rimsystem.mapper.RoadGeneralMapper;
import com.example.rimsystem.mapper.RoadMapper;
import com.example.rimsystem.service.RoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther luyu
 */
@Service
public class RoadServiceImpl implements RoadService {
    @Autowired
    RoadGeneralMapper roadGeneralMapper;
    @Autowired
    BranchRoadGeneralMapper branchRoadGeneralMapper;
    @Autowired
    RoadMapper roadMapper;
    @Autowired
    RoadDocGeneralMapper roadDocGeneralMapper;
    @Override
    public void insertOneRoad(Road road) {
        roadGeneralMapper.insert(road);
    }

    @Override
    public void updateCoordinateByRoadId(Integer roadId,String coordinate) {
        roadMapper.updateCoordinateByRoadId(roadId,coordinate);
    }

    @Override
    public String selectCoordinateById(Integer roadId) {
        return roadMapper.selectCoordinateById(roadId);
    }

    @Override
    public int deleteRoadById(Integer roadId) {
        Road road = new Road();
        road.setId(roadId);
        int delete1 = roadGeneralMapper.delete(road);
        BranchRoad branchRoad = new BranchRoad();
        branchRoad.setRoadId(roadId);
        int delete2 = branchRoadGeneralMapper.delete(branchRoad);
        RoadDoc roadDoc = new RoadDoc();
        roadDoc.setRoadId(roadId);
        int delete = roadDocGeneralMapper.delete(roadDoc);
        return delete+delete1+delete2;
    }

    @Override
    public List<Road> selectOneRoadByInfo(String name, String roadType, String roadMaintenance, PageBean<Road> pageBean) {
        Road road = new Road();
        road.setRoadName(name);
        road.setRoadType(roadType);
        road.setRoadMaintenanceGrade(roadMaintenance);
        int selectCount = roadGeneralMapper.selectCount(road);
        pageBean.setTotalCount(selectCount);
        int count = pageBean.getPageCount();
        int index = (pageBean.getCurrentPage() - 1) * count;
        List<Road> roads = roadMapper.selectOneRoadByInfo(name, roadType, roadMaintenance, index, count);
        return roads;

    }

    @Override
    public List<Road> selectAllPages(PageBean<Road> pageBean) {
        Road road = new Road();
        pageBean.setTotalCount(roadGeneralMapper.selectCount(road));
        if (pageBean.getCurrentPage() == 0) {
            pageBean.setCurrentPage(1);
        }
        else if(pageBean.getCurrentPage() == (pageBean.getTotalPage() + 1)) {
            pageBean.setCurrentPage(pageBean.getTotalPage());
        }
        int count = pageBean.getPageCount();
        int index = (pageBean.getCurrentPage() - 1) * count;
        List<Road> list =roadMapper.selectAllPages(index,count);
        return list;
    }
}
