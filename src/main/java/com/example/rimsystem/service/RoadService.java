package com.example.rimsystem.service;

import com.example.rimsystem.bean.PageBean;
import com.example.rimsystem.bean.Road;
import com.example.rimsystem.bean.RoadPicture;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RoadService {
    void insertOneRoad(Road road);
    List<Road> selectAllPages(PageBean<Road> pageBean);
    void updateCoordinateByRoadId(@Param("roadId") Integer roadId, String coordinate);
    String selectCoordinateById(Integer roadId);
    int deleteRoadById(Integer roadId);
    List<Road> selectOneRoadByInfo(String name, String roadType,String roadMaintenance,PageBean<Road> pageBean);
    void insertPicture(RoadPicture roadPicture);
    Road selectRoadDetail(Integer roadId);
    void updateRoadDetail(Road road);

}
