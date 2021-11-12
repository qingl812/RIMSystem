package com.example.rimsystem.service.impl;

import com.example.rimsystem.mapper.UserGeneralMapper;
import com.example.rimsystem.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @auther luyu
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserGeneralMapper userGeneralMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if(s==null||s.equals("")){
            throw  new RuntimeException("账号不能为空");
        }
        com.example.rimsystem.bean.User user = new com.example.rimsystem.bean.User();
        user.setUsername(s);
        com.example.rimsystem.bean.User selectOne = userGeneralMapper.selectOne(user);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(selectOne==null){
            throw new UsernameNotFoundException(s+"这个用户不存在");
        }
        else {
            List<String> roleNames = userMapper.selectUserAndRolesWithName(s);
            roleNames.forEach(role->{
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
                authorities.add(grantedAuthority);
            });
        }
        return new User(selectOne.getUsername(),selectOne.getPassword(),authorities);

    }
}
