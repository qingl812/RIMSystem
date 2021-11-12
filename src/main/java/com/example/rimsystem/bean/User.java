package com.example.rimsystem.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Id;

/**
 * @auther luyu
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private Integer id;
    private Integer account;
    private String username;
    private String password;
//    管理单位
    private String organizationName;
//        是否激活
    private Integer status;
}
