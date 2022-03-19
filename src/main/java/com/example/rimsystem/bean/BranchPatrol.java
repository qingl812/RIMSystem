package com.example.rimsystem.bean;

import lombok.Data;

import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @auther luyu
 */
@Data
@Table(name = "branch_patrol")
public class BranchPatrol {
    private Integer id;
    private BranchRoad branchRoad;
    private String locationDescribe;
    private String imagePath;
    private String accessoryPath;
    private Integer patrolLogId;
    private PatrolLog patrolLog;
    private String remark;
    private Timestamp finishTime;
    private Road road;
}
