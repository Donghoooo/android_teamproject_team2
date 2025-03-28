package bitc.fullstack503.moneylog_spring.service;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;

import java.util.List;

public interface AnalyzeService {
    List<IncomeLogDTO> selectincomeList(String startDate, String endDate, String memberId) throws Exception;

    List<ExpenseLogDTO> selectexpenseList(String startDate, String endDate, String memberId) throws Exception;

//    List<IncomeLogDTO> selectTimeList(String timeStart, String timeEnd) throws Exception;
}
