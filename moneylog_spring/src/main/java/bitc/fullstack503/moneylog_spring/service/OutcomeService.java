package bitc.fullstack503.moneylog_spring.service;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;

public interface OutcomeService {
    void expense(ExpenseLogDTO outcome) throws Exception;
}
