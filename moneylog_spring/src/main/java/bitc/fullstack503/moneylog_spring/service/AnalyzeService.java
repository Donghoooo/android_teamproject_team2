package bitc.fullstack503.moneylog_spring.service;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;

import java.util.List;

public interface AnalyzeService {
    List<IncomeLogDTO> selectincomeList() throws Exception;

    List<ExpenseLogDTO> selectexpenseList() throws Exception;

//    List<IncomeLogDTO> selectTimeList(String timeStart, String timeEnd) throws Exception;
}
