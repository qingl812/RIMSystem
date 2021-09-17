package com.example.rimsystem.mapper;

import com.example.rimsystem.bean.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserGeneralMapper extends Mapper<User> {
}
