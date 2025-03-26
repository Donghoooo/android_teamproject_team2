package bitc.fullstack503.moneylog_spring.service;
import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;

import java.util.List;

public interface OutcomeService {

    void expense(ExpenseLogDTO outcome) throws Exception;

//    // 특정 날짜에 대한 수입과 지출을 조회하는 메서드
//    List<ExpenseDataDTO> getIncomeAndExpenseByDate(String date);
//
//    // 특정 월에 대한 날짜별 수입과 지출을 조회하는 메서드
//    List<ExpenseDataDTO> getIncomeAndExpenseByMonth(int year, int month);

}
