package bitc.fullstack503.moneylog_spring.service;

import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;

public interface IncomeService {
    void income(IncomeLogDTO income) throws Exception;
}
