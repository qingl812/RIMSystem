package com.example.rimsystem.mapper;

import com.example.rimsystem.bean.RoadPicture;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface RoadPicMapper extends Mapper<RoadPicture> {
    void insertRoadAndPicRelation(RoadPicture roadPicture);
}
