package com.example.rimsystem.service;

import com.example.rimsystem.bean.RoadDoc;
import org.springframework.stereotype.Service;


public interface RoadDocService {
    int updateDocPath(String filePath,Integer roadId);
    RoadDoc selectDocPath(Integer docId);
}
