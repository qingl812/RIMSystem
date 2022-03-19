package com.example.rimsystem.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;

/**
 * @auther luyu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fund")
public class Fund {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
//    月度资金名
    private String fundName;
//    编制单位
    private String organization;
//    维修时间
    private Timestamp refixTime;
//    维修总价
    private Double totalPrice;
//    已支付的金额
    private Double getPrice;
//    编制时间（也就是创建这个月度资金表的时间）
    private Timestamp fundTime;
//    拨款时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Timestamp getPriceTime;
//    收款单位
    private String getPriceOrganization;
    private Integer planId;
    @Column(name = "year_fund_id")
    private Integer yearFundId;
    List<MonthFund> monthFunds;
}
