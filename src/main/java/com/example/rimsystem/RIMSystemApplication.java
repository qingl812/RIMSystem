package com.example.rimsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication

@MapperScan(basePackages = {"com.example.rimsystem.mapper"})
public class RIMSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(RIMSystemApplication.class, args);
    }
}
