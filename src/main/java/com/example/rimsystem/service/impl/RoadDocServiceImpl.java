package com.example.rimsystem.service.impl;

import com.example.rimsystem.bean.RoadDoc;
import com.example.rimsystem.mapper.RoadDocTKMapper;
import com.example.rimsystem.mapper.RoadDocMapper;
import com.example.rimsystem.service.RoadDocService;
import com.example.rimsystem.seucurity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = IOException.class)
    public Result deleteRoadDoc(int docId) {
        RoadDoc roadDoc = new RoadDoc();
        roadDoc.setId(docId);
        RoadDoc completeRoadDoc = roadDocTKMapper.selectOne(roadDoc);
        String docPath = completeRoadDoc.getDocPath();
        int delete = roadDocTKMapper.delete(roadDoc);
        FileSystemUtils.deleteRecursively(new File(docPath));
        if(delete>0){
            return Result.ok().message("删除成功");
        }
        else {
            return Result.error();
        }
    }

    @Override
    public List<String> selectAllRoadDocType() {
        return roadDocMapper.selectAllRoadDocType();
    }

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
