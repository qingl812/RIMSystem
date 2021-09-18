package com.example.rimsystem.service.impl;


import com.example.rimsystem.bean.User;
import com.example.rimsystem.mapper.UserGeneralMapper;
import com.example.rimsystem.mapper.UserMapper;
import com.example.rimsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserGeneralMapper userGeneralMapper;
    @Override
    public User selectUserByName(String name) {
        User user = new User();
        user.setName(name);
        return userGeneralMapper.selectOne(user);
    }

}
