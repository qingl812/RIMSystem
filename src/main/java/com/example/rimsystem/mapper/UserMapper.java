package com.example.rimsystem.mapper;

import com.example.rimsystem.bean.Role;
import com.example.rimsystem.bean.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {


    List<Role> selectUserAndRolesWithName(String s);

    List<String> selectPerCodeByRoleId(List<Role> roles);
    @Select("select distinct organization_name from user" )
    List<String> selectAllManagement();
    User selectUserByUserName(String username);
    @Select("select username from user")
    List<String> selectAllUsername();
}

