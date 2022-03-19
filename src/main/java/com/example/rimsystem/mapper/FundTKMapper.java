package com.example.rimsystem.mapper;

import com.example.rimsystem.bean.BranchPatrol;
import com.example.rimsystem.bean.Fund;
import com.example.rimsystem.bean.MonthFund;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;

@Repository
public interface FundTKMapper extends Mapper<Fund>, InsertListMapper<MonthFund> {
    List<BranchPatrol> selectMonthFundByPlanId(Integer planId);
    @Update("update fund set total_price=#{param1} where id=#{param2}")
    void updateTotalPrice(double totalPrice, Integer planId);
    @Update("update maintenance_plan set total_price=#{param1} where id=#{param2}")
    void updatePlanTotalPrice(double totalPrice, Integer planId);
    Fund selectFundByPlanId(Integer planId);
    @Update("update year_fund set no_give_money=#{param1} where id=#{param2}")
    void updateYearFundNoGiveMoney(Double needToGivePrice, Integer id);
    @Update("update fund set get_price=#{getPrice},get_price_time=#{getPriceTime},get_price_organization=#{getPriceOrganization} " +
            "where id=#{id}")
    void saveManagement(Fund fund);
}
