package com.example.rimsystem.mapper;

import com.example.rimsystem.bean.Road;
import org.springframework.stereotype.Repository;

@Repository
public interface RoadMapper {
//    通过道路id查询道路的所有信息，包括支路的信息
    public Road selectOneRoad(int roadNum);

}
