package com.example.rimsystem.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;

/**
 * @auther luyu
 */
@Table(name = "maintenance_plan")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenancePlan {
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private Timestamp planTime;
    private Double totalPrice;
    private String isAct;
    private String isFinish;
    private List<BranchPatrol> branchPatrols;

    public MaintenancePlan(Integer id, Timestamp planTime, Double totalPrice, String isAct, String isFinish) {
        this.id = id;
        this.planTime = planTime;
        this.totalPrice = totalPrice;
        this.isAct = isAct;
        this.isFinish = isFinish;
    }
}
