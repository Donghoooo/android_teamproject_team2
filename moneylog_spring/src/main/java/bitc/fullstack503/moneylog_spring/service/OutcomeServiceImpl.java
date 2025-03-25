package bitc.fullstack503.moneylog_spring.service;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.mapper.OutcomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutcomeServiceImpl implements OutcomeService {

    @Autowired
    private OutcomeMapper outcomeMapper;

// 지출 내용 입력하기
    @Override
    public void expense(ExpenseLogDTO outcome) throws Exception {
        outcomeMapper.expense(outcome);
    }

//    지출 내용 수정하기
    @Override
    public int expenseUpdate(int expenseLogSeq) throws Exception {
        return outcomeMapper.expenseUpdate(expenseLogSeq);
    }

//    지출 내용 삭제하기
    @Override
    public int expenseDelete(int expenseLogSeq) throws Exception {
        return outcomeMapper.expenseDelete(expenseLogSeq);
    }
}
