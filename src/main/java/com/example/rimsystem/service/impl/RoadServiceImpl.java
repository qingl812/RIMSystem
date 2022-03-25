package com.example.rimsystem.service.impl;

import com.example.rimsystem.bean.*;
import com.example.rimsystem.mapper.*;
import com.example.rimsystem.service.RoadService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @auther luyu
 */
@Service
public class RoadServiceImpl implements RoadService {
    @Autowired
    BranchRoadGeneralMapper branchRoadGeneralMapper;
    @Autowired
    RoadMapper roadMapper;
    @Autowired
    RoadDocTKMapper roadDocTKMapper;
    @Autowired
    RoadPicMapper roadPicMapper;
    @Autowired
    RoadTKMapper roadTKMapper;

    @Override
    public PageInfo<Road> selectHomePageRoad(String unit, String roadName, Integer currentPage, Integer pageSize) {
        StringBuffer stringBuffer = new StringBuffer("%"+roadName+"%");
        PageHelper.startPage(currentPage,pageSize);
        List<Road> roads = roadMapper.selectAllHomePage(unit, stringBuffer.toString());
        PageInfo<Road> pageInfo = new PageInfo(roads);
        pageInfo.setList(roads);
        return pageInfo;
    }

    @Override
    public void updateRoadDetail(Road road) {
        Example example = new Example(Road.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",road.getId());
        roadTKMapper.updateByExampleSelective(road,example);
    }

    @Override
    public Road selectRoadDetail(Integer roadId) {
        Road road = new Road();
        road.setId(roadId);
        return roadTKMapper.selectOne(road);
    }

    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    @Override
    public void insertPicture(RoadPicture roadPicture) {
        roadPicMapper.insert(roadPicture);
        roadPicMapper.insertRoadAndPicRelation(roadPicture);
    }

    @Override
    public void insertOneRoad(Road road) {
        roadTKMapper.insert(road);
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
        int delete1 = roadTKMapper.delete(road);
        BranchRoad branchRoad = new BranchRoad();
        branchRoad.setRoadId(roadId);
        int delete2 = branchRoadGeneralMapper.delete(branchRoad);
        RoadDoc roadDoc = new RoadDoc();
        roadDoc.setRoadId(roadId);
        int delete = roadDocTKMapper.delete(roadDoc);
        return delete+delete1+delete2;
    }

    @Override
    public List<Road> selectOneRoadByInfo(String name, String roadType, String roadMaintenance, PageBean<Road> pageBean) {
        Road road = new Road();
        road.setRoadName(name);
        road.setRoadType(roadType);
        road.setRoadMaintenanceGrade(roadMaintenance);
        int selectCount = roadTKMapper.selectCount(road);
        pageBean.setTotalCount(selectCount);
        int count = pageBean.getPageCount();
        int index = (pageBean.getCurrentPage() - 1) * count;
        List<Road> roads = roadMapper.selectOneRoadByInfo(name, roadType, roadMaintenance, index, count);
        return roads;

    }

    @Override
    public List<Road> selectAllPages(PageBean<Road> pageBean) {
        Road road = new Road();
        pageBean.setTotalCount(roadTKMapper.selectCount(road));
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
