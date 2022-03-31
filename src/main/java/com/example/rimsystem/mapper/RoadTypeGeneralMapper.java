package com.example.rimsystem.mapper;

import com.example.rimsystem.bean.RoadType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface RoadTypeGeneralMapper extends Mapper<RoadType> {
    @Select("select grade from maintenance_grade")
    List<String> selectAllMaintenanceGrade();
}
