package bitc.fullstack503.moneylog_spring.mapper;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OutcomeMapper {
    public void expense(ExpenseLogDTO outcome)throws Exception;

}
