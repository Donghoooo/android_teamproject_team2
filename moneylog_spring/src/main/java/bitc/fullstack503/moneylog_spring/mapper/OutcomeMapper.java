package bitc.fullstack503.moneylog_spring.mapper;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OutcomeMapper {
    public void expense(ExpenseLogDTO outcome)throws Exception;
}
