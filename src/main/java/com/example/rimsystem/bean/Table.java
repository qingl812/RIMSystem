package com.example.rimsystem.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @auther luyu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@javax.persistence.Table(name = "approve_table")
public class Table {
    @Id
    private Integer id;
    private String projectName;
    private String projectType;
    private String projectAddress;
    @JsonFormat(pattern = "yyyy/MM/dd",timezone = "Asia/Shanghai")
    private Timestamp startTime;
    @JsonFormat(pattern = "yyyy/MM/dd",timezone = "Asia/Shanghai")
    private Timestamp endTime;
    private Double projectPrice;
//    施工单位
    private String construction;
    private String projectDetail;
    private String advise;
//    经办人
    private String responsible_person1;
//    分管人
    private String responsible_person2;
    @JsonFormat(pattern = "yyyy/MM/dd",timezone = "Asia/Shanghai")
    private Timestamp approveTime;
    @Column(name = "create_table_time")
    private Timestamp createTableTime;
    private Integer planId;
}
