package com.example.rimsystem.service.impl;

import com.example.rimsystem.bean.RoadDoc;
import com.example.rimsystem.mapper.RoadDocTKMapper;
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
    @Autowired
    RoadDocTKMapper roadDocTKMapper;
    @Override
    public int updateDocPath(String filePath, Integer roadId) {
            return roadDocMapper.updateDocPath(filePath,roadId);
    }

    @Override
    public RoadDoc selectDocPath(Integer docId) {
        RoadDoc roadDoc = new RoadDoc();
        roadDoc.setId(docId);
        RoadDoc docPath = roadDocTKMapper.selectOne(roadDoc);
        return docPath;
    }

    @Override
    public void insertDoc(RoadDoc roadDoc) {
        roadDocTKMapper.insert(roadDoc);
    }
}
