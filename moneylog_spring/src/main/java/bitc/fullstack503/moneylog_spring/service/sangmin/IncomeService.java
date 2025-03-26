package bitc.fullstack503.moneylog_spring.service.sangmin;

import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;

public interface IncomeService {
//    수입 내용 입력하기
    void income(IncomeLogDTO income) throws Exception;

//    수입 내용 수정하기
    int incomeUpdate(IncomeLogDTO incomeLog)throws Exception;


//    수입 내용 삭제하기
    int incomeDelete(int incomeLogSeq) throws Exception;

}
