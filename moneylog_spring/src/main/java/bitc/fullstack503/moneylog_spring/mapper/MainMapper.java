package bitc.fullstack503.moneylog_spring.mapper;
import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MainMapper
{
    List<IncomeLogDTO> selectIncomeMain(String year, String month, String monthPlus, String memberId) throws Exception;

    List<ExpenseLogDTO> selectExpenseMain(String year, String month, String monthPlus, String memberId) throws Exception;

}
