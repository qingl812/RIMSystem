package com.example.rimsystem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.rimsystem.Schedule;
import com.example.rimsystem.bean.AccountUser;
import com.example.rimsystem.filter.RedisBloomFilter;
import com.example.rimsystem.mapper.UserGeneralMapper;
import com.example.rimsystem.mapper.UserMapper;
import com.example.rimsystem.service.UserService;
import com.example.rimsystem.tool.BloomFilterHelper;
import com.example.rimsystem.tool.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @auther luyu
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private Schedule schedule;

    @Autowired
    private UserService userService;

    @Autowired
    private UserGeneralMapper userGeneralMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException,DisabledException {
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

        String userJson = JSONObject.toJSONString(selectOne);
        String s1 = redisTemplate.opsForValue().get("uid:" + selectOne.getId());
        if(s1==""||s1==null){
            redisTemplate.opsForValue().set("uid:" + selectOne.getId(), userJson, 3, TimeUnit.HOURS);
        }
        return new AccountUser(selectOne.getId(),selectOne.getUsername(),selectOne.getPassword(), authorities);

    }
    public List<GrantedAuthority> getRoleStringToList(String username){
        if(Schedule.getRedisBloomFilter().contains(Schedule.getBloomFilterHelper(),"bloomUsername",username)){
            String rolesAndPer = userService.selectRolesAndPerByUsername(username,Schedule.getRedisBloomFilter());
            return AuthorityUtils.commaSeparatedStringToAuthorityList(rolesAndPer);
        }
        else {
            throw new UsernameNotFoundException(username+"这个用户不存在");
        }

    }
}
