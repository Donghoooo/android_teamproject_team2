package bitc.fullstack503.moneylog_spring.mapper;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnalyzeMapper {
    List<IncomeLogDTO> selectincomeList(String startDate, String endDate, String memberId) throws Exception;

    List<ExpenseLogDTO> selectexpenseList(String startDate, String endDate, String memberId)throws Exception;

//    List<IncomeLogDTO> selectTimeList()throws Exception;
}
