package bitc.fullstack503.moneylog_spring.service;

import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import bitc.fullstack503.moneylog_spring.mapper.IncomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeMapper incomeMapper;

    @Override
    public void income(IncomeLogDTO income) throws Exception {
        incomeMapper.income(income);
    }
}
