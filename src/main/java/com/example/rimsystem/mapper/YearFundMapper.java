package com.example.rimsystem.mapper;

import com.example.rimsystem.bean.YearFund;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface YearFundMapper extends Mapper<YearFund> {
    @Update("update year_fund set money = #{param2} where id=#{param1}")
    void updateYearFundMoney(Integer yearFundId, Double money);
}
