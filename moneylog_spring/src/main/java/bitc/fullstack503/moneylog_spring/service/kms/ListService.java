package bitc.fullstack503.moneylog_spring.service.kms;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;

import java.util.List;

public interface ListService {
    List<IncomeLogDTO> selectincomeList() throws Exception;

    List<ExpenseLogDTO> selectExpenseList() throws Exception;
}
