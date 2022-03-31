package com.example.rimsystem.service;

import com.example.rimsystem.bean.User;
import com.example.rimsystem.filter.RedisBloomFilter;
import com.example.rimsystem.seucurity.Result;

import java.util.List;

public interface UserService {
//    User selectUserByName(String name,String password);
    String selectRolesAndPerByUsername(String username, RedisBloomFilter redisBloomFilter);

    List<String> selectAllManagement();

    User selectUserInfoByUserName(String username);

    List<String> selectAllUsername();
}
