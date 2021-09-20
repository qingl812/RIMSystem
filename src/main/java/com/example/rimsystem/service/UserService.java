package com.example.rimsystem.service;

import com.example.rimsystem.bean.User;

public interface UserService {
    User selectUserByName(String name,String password);
}
