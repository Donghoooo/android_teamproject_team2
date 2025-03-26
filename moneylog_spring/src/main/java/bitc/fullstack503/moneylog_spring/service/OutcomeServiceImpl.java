package bitc.fullstack503.moneylog_spring.service;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.mapper.OutcomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutcomeServiceImpl implements OutcomeService {

    @Autowired
    private OutcomeMapper outcomeMapper;

    @Override
    public void expense(ExpenseLogDTO outcome) throws Exception {
        outcomeMapper.expense(outcome);
    }

//    @Override
//    public List<ExpenseDataDTO> getIncomeAndExpenseByDate(String date) {
//        // MyBatis mapper를 통해 날짜별 수입과 지출을 조회
//        return outcomeMapper.selectIncomeAndExpenseByDate(date); // 메서드 이름을 일치시킴
//    }
//
//    @Override
//    public List<ExpenseDataDTO> getIncomeAndExpenseByMonth(int year, int month) {
//        // MyBatis mapper를 통해 특정 월에 대한 날짜별 수입과 지출을 조회
//        return outcomeMapper.selectIncomeAndExpenseByMonth(year, month); // 메서드 이름을 일치시킴
//    }
}
