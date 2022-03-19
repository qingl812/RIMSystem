package com.example.rimsystem.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @auther luyu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "log")
public class UserLog {
    @Id
    private Integer id;
    private String username;
    private String operation;
    private Integer time;
    private String method;
    private String params;
    private Timestamp createTime;
    private String ip;
}
