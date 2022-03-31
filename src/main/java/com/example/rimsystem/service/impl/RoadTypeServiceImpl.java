package com.example.rimsystem.service.impl;

import com.example.rimsystem.bean.RoadType;
import com.example.rimsystem.mapper.RoadMapper;
import com.example.rimsystem.mapper.RoadTypeGeneralMapper;
import com.example.rimsystem.service.RoadTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther luyu
 */
@Service
public class RoadTypeServiceImpl implements RoadTypeService {
    @Autowired
    RoadTypeGeneralMapper roadTypeGeneralMapper;
    @Override
    public List<String> searchMaintenanceGrade() {
        return roadTypeGeneralMapper.selectAllMaintenanceGrade();
    }

    @Override
    public List<RoadType> searchRoadType() {
        List<RoadType> roadList = roadTypeGeneralMapper.selectAll();
        return roadList;
    }
}
