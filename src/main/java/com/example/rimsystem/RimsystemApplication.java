package com.example.rimsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RimsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(RimsystemApplication.class, args);
    }

    public RestTemplate restTemplate(){
        new RestTemplate();
    }
}
