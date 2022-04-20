package com.example.rimsystem.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoadDocMapper {
    public int updateDocPath(String docPath,Integer id);
    List<String> selectAllRoadDocType();
}
