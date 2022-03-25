package com.example.rimsystem.service.impl;


import com.example.rimsystem.bean.Role;
import com.example.rimsystem.bean.User;
import com.example.rimsystem.mapper.UserGeneralMapper;
import com.example.rimsystem.mapper.UserMapper;
import com.example.rimsystem.service.UserService;
import com.example.rimsystem.seucurity.Result;
import com.example.rimsystem.tool.JwtTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
//    @Override
//    public User selectUserByName(String name,String password) {
//        User user = new User();
//        user.setUsername(name);
//        user.setPassword(password);
//        return userGeneralMapper.selectOne(user);
//    }


    @Override
    public User selectUserInfoByUserName(String username) {
        return userMapper.selectUserByUserName(username);
    }

    @Override
    public List<String> selectAllManagement() {
        return userMapper.selectAllManagement();
    }

    @Override
    public String selectRolesAndPerByUsername(String username) {
        String authorities="";
        if(!redisTemplate.opsForValue().getOperations().hasKey("authority"+username)){
//        先通过username拿到用户的角色，并且用'，'分开
            List<Role> roles = userMapper.selectUserAndRolesWithName(username);
            if(roles.size()>0){
                String collect = roles.stream().map(role -> role.getRoleName()).collect(Collectors.joining(","));
                authorities = collect+",";
            }
//        通过角色的id拿到该用户拥有的权限代码集
            List<String> perCode =  userMapper.selectPerCodeByRoleId(roles);
            if (perCode.size()>0){
                String join = StringUtils.join(perCode, ",");
                authorities+=join;
            }
            redisTemplate.opsForValue().set("authority"+username,authorities,60*60*2, TimeUnit.SECONDS);
        }
        else {
            authorities = (String) redisTemplate.opsForValue().get("authority"+username);
        }
        return authorities;
    }
}
