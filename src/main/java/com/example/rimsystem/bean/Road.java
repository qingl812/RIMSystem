package com.example.rimsystem.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Id;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Road {
    private String roadName;
//    道路类型
    private String roadType;
    @Id
    private Integer roadNum;
    private Double roadLength;
//    道路分段数
    private Integer roadSectionNum;
//    道路路面类型
    private String roadPavementType;
//    人行道面砖类型
    private String sidewalkBrickType;
//    道路养护等级
    private String roadMaintenanceGrade;
//    盲道面砖类型
    private String blindBrickType;
//    路缘石（侧石）类型
    private String curbType;
//    某个主道路的各个支路
    private List<BranchRoad> branchRoads;

}
