package bitc.fullstack503.moneylog_spring.service;
import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;

import java.util.List;

public interface MainService
{
    List<IncomeLogDTO> selectIncomeMain(String year, String month, String monthPlus, String memberId) throws Exception;

    List<ExpenseLogDTO> selectExpenseMain(String year, String month, String monthPlus, String memberId) throws Exception;

//  List<MainDTO> mainList (MemberDTO memberDTO) throws Exception;


}
