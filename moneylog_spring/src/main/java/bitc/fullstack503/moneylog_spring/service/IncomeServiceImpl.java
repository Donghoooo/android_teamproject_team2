package bitc.fullstack503.moneylog_spring.service;

import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import bitc.fullstack503.moneylog_spring.mapper.IncomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeMapper incomeMapper;

//    수입 내용 입력하기
    @Override
    public void income(IncomeLogDTO income) throws Exception {
        incomeMapper.income(income);
    }

//    수입 내용 수정하기
@Override
public int incomeUpdate(IncomeLogDTO incomeLog)throws Exception {
    return incomeMapper.incomeUpdate(incomeLog);
}
//    수입 내용 삭제하기
    @Override
    public int incomeDelete(int incomeLogSeq) throws Exception {
        return incomeMapper.incomeDelete(incomeLogSeq);
    }


}
