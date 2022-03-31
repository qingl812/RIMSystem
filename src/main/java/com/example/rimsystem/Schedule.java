package com.example.rimsystem;

import com.example.rimsystem.filter.RedisBloomFilter;
import com.example.rimsystem.mapper.UserMapper;
import com.example.rimsystem.service.UserService;
import com.example.rimsystem.service.impl.UserDetailServiceImpl;
import com.example.rimsystem.tool.BloomFilterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @auther luyu
 */
@Component
public class Schedule {
    @Autowired
    private RedisBloomFilter redisBloomFilter1 ;
    private static RedisBloomFilter redisBloomFilter;
    private static final BloomFilterHelper<String> bloomFilterHelper = new BloomFilterHelper<>(100000);

    @Autowired
    private UserService userService;
    private static UserService userServiceImpl;
    //    这个注解使得，在初始化后执行。为什么不用static（学过jvm可知，clinit类初始化方法，初始化静态变量和静态代码块
//    在spring自动注入前就执行，因此在static中根本调用不到spring注入的service）
    @PostConstruct
    public void init(){
        redisBloomFilter=redisBloomFilter1;
        redisBloomFilter.delete("bloomUsername");
        userServiceImpl = userService;
        List<String> usernameList = userServiceImpl.selectAllUsername();
        redisBloomFilter.addList(bloomFilterHelper,"bloomUsername",usernameList);

    }

    public static RedisBloomFilter getRedisBloomFilter(){
        return redisBloomFilter;
    }
    public static void setRedisBloomFilter(List<String> usernameList){
        redisBloomFilter.delete("bloomUsername");
        redisBloomFilter.addList(bloomFilterHelper,"bloomUsername",usernameList);
    }

    public static BloomFilterHelper getBloomFilterHelper(){
        return bloomFilterHelper;
    }

    @Autowired
    private UserMapper userMapper;
    @Scheduled(cron = "*/120 * * * * *")
    public void readUsernameTask(){
        List<String> strings = userMapper.selectAllUsername();
        setRedisBloomFilter(strings);
    }
}
