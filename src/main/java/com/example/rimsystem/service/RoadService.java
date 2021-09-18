package com.example.rimsystem.service;

import com.example.rimsystem.bean.PageBean;
import com.example.rimsystem.bean.Road;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RoadService {
    public void insertOneRoad(Road road);
    public List<Road> selectAllPages(PageBean<Road> pageBean);
}
