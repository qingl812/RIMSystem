package com.example.rimsystem.service;

import com.example.rimsystem.bean.User;
import com.example.rimsystem.seucurity.Result;

public interface UserService {
//    User selectUserByName(String name,String password);
    String selectRolesAndPerByUsername(String username);
}
