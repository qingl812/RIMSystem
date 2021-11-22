package com.example.rimsystem.service.impl;

import com.example.rimsystem.mapper.RoadDocGeneralMapper;
import com.example.rimsystem.mapper.RoadDocMapper;
import com.example.rimsystem.service.RoadDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther luyu
 */
@Service
public class RoadDocServiceImpl implements RoadDocService {
    @Autowired
    RoadDocMapper roadDocMapper;
    @Override
    public int updateDocPath(String filePath, Integer roadId) {
            return roadDocMapper.updateDocPath(filePath,roadId);
    }
}
