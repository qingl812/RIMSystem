package com.example.rimsystem.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * @auther luyu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "year_fund")
public class YearFund {
    private Integer id;
    private Integer year;
    private Double money;
    private Double hadGiveMoney;
    private Double noGiveMoney;
}
