package com.example.rimsystem.mapper;

import com.example.rimsystem.bean.Road;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface RoadTKMapper extends Mapper<Road> {
}
