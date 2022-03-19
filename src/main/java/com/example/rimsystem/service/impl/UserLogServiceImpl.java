package com.example.rimsystem.service.impl;

import com.example.rimsystem.bean.UserLog;
import com.example.rimsystem.mapper.UserLogTKMapper;
import com.example.rimsystem.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther luyu
 */
@Service
public class UserLogServiceImpl implements UserLogService {
    @Autowired
    UserLogTKMapper userLogTKMapper;
    @Override
    public void insert(UserLog userLog) {
        userLogTKMapper.insert(userLog);
    }
}
