package com.example.rimsystem.mapper;

import com.example.rimsystem.bean.Road;
import org.springframework.stereotype.Repository;

@Repository
public interface RoadMapper {
    public Road selectOneRoad(int roadNum);
}
