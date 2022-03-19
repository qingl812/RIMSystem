package com.example.rimsystem.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @auther luyu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "month_fund")
public class MonthFund implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private String projectName;
    private String projectType;
    private String projectContent;
    private String unit;
    private Double number;
    private Double unitPrice;
    private Double unitTotalPrice;
    private String remark;
    private Integer fundId;
}
