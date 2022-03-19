package com.example.rimsystem.service;

import com.example.rimsystem.bean.BranchRoad;

import java.util.List;


public interface BranchRoadService {
    public void insertOneBranchRoad(BranchRoad branchRoad);


    List<String> showImageType();
}
