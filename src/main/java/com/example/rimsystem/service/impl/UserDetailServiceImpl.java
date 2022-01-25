package com.example.rimsystem.service.impl;

import com.example.rimsystem.bean.AccountUser;
import com.example.rimsystem.mapper.UserGeneralMapper;
import com.example.rimsystem.mapper.UserMapper;
import com.example.rimsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if(s==null||s.equals("")){
            throw  new RuntimeException("账号不能为空");
        }
        com.example.rimsystem.bean.User user = new com.example.rimsystem.bean.User();
        user.setUsername(s);
        com.example.rimsystem.bean.User selectOne = userGeneralMapper.selectOne(user);
        List<? extends GrantedAuthority> authorities = new ArrayList<>();
        if(selectOne==null){
            throw new UsernameNotFoundException(s+"这个用户不存在");
        }
        else {
            authorities = getRoleStringToList(s);
        }
        return new AccountUser(selectOne.getId(),selectOne.getUsername(),selectOne.getPassword(),authorities);

    }
    public List<GrantedAuthority> getRoleStringToList(String username){
        String rolesAndPer = userService.selectRolesAndPerByUsername(username);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(rolesAndPer);

    }
}
