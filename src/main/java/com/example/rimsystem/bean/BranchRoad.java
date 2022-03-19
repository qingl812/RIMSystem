package com.example.rimsystem.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Map;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "branch_road")
public class BranchRoad {
    public String getBranchName() {
        return branchName;
    }

    @Id
    private Integer id;
    private Integer branchNum;
    private String branchName;
    private Double branchLength;
    private String startLocation;
    private String endLocation;
    //    起止坐标
    private String startAndEndCoordinates;
    //    路面类型
    private String branchPavementType;
    //    人行道面砖类型
    private String branchSidewalkBrickType;
    //    盲道面砖类型
    private String branchBlindBrickType;
    //    路缘石（侧石）类型
    private String branchCurbType;
    //      备注
    private String remark;
    //    外键
    private Integer roadId;
//    路面破损面积
    private Double pavementBreak;
//    人行道面砖破损
    private Double sidewalkBrickBreak;
//  盲道面破损面积
    private Double blindBrickBreak;
//    路缘石（侧石）破损面积
    private Double curbBreak;

    public BranchRoad(String branchName,Integer branchNum,Double branchLength,Integer roadId){
        this.branchName=branchName;
        this.branchNum=branchNum;
        this.branchLength=branchLength;
        this.roadId=roadId;
    }
}
