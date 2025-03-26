package bitc.fullstack503.moneylog_spring.service;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;

import java.util.List;

public interface AnalyzeService {
    List<IncomeLogDTO> selectincomeList() throws Exception;

    List<ExpenseLogDTO> selectexpenseList() throws Exception;
}
