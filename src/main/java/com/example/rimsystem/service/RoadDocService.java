package com.example.rimsystem.service;

import com.example.rimsystem.bean.RoadDoc;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RoadDocService {
    int updateDocPath(String filePath,Integer roadId);
    RoadDoc selectDocPath(Integer docId);
    List<String> selectAllRoadDocType();
    void insertDoc(RoadDoc roadDoc);
}
