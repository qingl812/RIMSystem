package com.example.rimsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.rimsystem.mapper")
public class RIMSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(RIMSystemApplication.class, args);
    }

}