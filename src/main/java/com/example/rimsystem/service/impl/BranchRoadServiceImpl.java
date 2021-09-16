package com.example.rimsystem.service.impl;

import com.example.rimsystem.bean.BranchRoad;
import com.example.rimsystem.mapper.BranchRoadGeneralMapper;
import com.example.rimsystem.mapper.BranchRoadMapper;
import com.example.rimsystem.service.BranchRoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther luyu
 */
@Service
public class BranchRoadServiceImpl implements BranchRoadService {
    @Autowired
    BranchRoadGeneralMapper branchRoadGeneralMapper;
    @Autowired
    BranchRoadMapper branchRoadMapper;
    public void insertOneBranchRoad(BranchRoad branchRoad){
        branchRoadGeneralMapper.insert(branchRoad);
    }

}
