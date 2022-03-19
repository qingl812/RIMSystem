package com.example.rimsystem.mapper;

import com.example.rimsystem.bean.Road;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoadMapper {
//    通过道路id查询道路的所有信息，包括支路的信息
     Road selectOneRoad(int roadNum);
     List<Road> selectAllPages(int index,int count);
    void updateCoordinateByRoadId(Integer roadId, String coordinate);
    String selectCoordinateById(Integer roadId);
    List<Road> selectOneRoadByInfo(String name, String roadType, String roadMaintenance,int index,int count);
    List<Road> selectAllRoad(String roadName,String roadType,String roadMaintenanceGrade, int index, int count);
//    @Select("insert into road_picture(id,picture_name,picture_type,remark,picture_path) values(null,#{param1),#{param4},#{param3},#{param2}")
//    void insertRoadPic(String picName,String picPath,String remark,String pictureType);
}
