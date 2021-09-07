package com.example.rimsystem.bean;

import lombok.Data;

import java.util.List;

/**
 * @auther luyu
 */
@Data
public class Road {
    private String roadName;
//    道路类型
    private String roadType;
    private Integer roadNum;
    private Double roadLength;
//    道路分段数
    private Integer roadSectionNum;
//    道路路面类型
    private String roadPaveMentType;

//    道路养护等级
    private String roadMaintenanceGrade;
//    某个主道路的各个支路
    private List<BranchRoad> branchRoads;

}
