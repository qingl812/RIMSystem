package com.example.rimsystem.service.impl;

import com.example.rimsystem.bean.PageBean;
import com.example.rimsystem.bean.Road;
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
    RoadMapper roadMapper;

    @Override
    public void insertOneRoad(Road road) {
        roadGeneralMapper.insert(road);
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
