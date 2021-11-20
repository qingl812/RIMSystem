package com.example.rimsystem.mapper;

import com.example.rimsystem.bean.Road;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoadMapper {
//    通过道路id查询道路的所有信息，包括支路的信息
    public Road selectOneRoad(int roadNum);
    public List<Road> selectAllPages(int index,int count);
    void updateCoordinateByRoadId(Integer roadId, String coordinate);
    String selectCoordinateById(Integer roadId);
}
