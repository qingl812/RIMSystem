package com.example.rimsystem.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @auther luyu
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "road_doc")
public class RoadDoc {
    private Integer id;
    private Integer roadId;
    private String docName;
//    文档存储路径
    private String docPath;
    private String docType;
//    归档单位
    private String docArchiveDepartment;
//    归档号
    private String docArchiveNum;
//    份号
    private Integer docPartNumber;
//    页数
    private Integer docPages;
//    备注
    private String docRemark;
//
    private Timestamp uploadTime;
}
