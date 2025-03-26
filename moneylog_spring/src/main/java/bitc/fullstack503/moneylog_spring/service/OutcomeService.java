package bitc.fullstack503.moneylog_spring.service;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;

public interface OutcomeService {

//    지출 내용 입력하기
    void expense(ExpenseLogDTO outcome) throws Exception;

//    지출 내용 수정하기
    int expenseUpdate(ExpenseLogDTO outcomeLog)throws Exception;


//    지출 내용 삭제하기
    int expenseDelete(int expenseLogSeq)throws Exception;

}
