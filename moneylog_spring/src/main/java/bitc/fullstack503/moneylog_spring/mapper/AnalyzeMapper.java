package bitc.fullstack503.moneylog_spring.mapper;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnalyzeMapper {
    List<IncomeLogDTO> selectincomeList() throws Exception;

    List<ExpenseLogDTO> selectexpenseList()throws Exception;
}
