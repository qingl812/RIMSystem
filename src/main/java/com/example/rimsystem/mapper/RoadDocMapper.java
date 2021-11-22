package com.example.rimsystem.mapper;

import org.springframework.stereotype.Repository;

@Repository
public interface RoadDocMapper {
    public int updateDocPath(String docPath,Integer id);
}
