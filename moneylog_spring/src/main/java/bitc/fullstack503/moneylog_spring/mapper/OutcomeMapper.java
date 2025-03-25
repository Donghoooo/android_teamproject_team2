package bitc.fullstack503.moneylog_spring.mapper;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OutcomeMapper {
//     지출 내용 입력하기
    public void expense(ExpenseLogDTO outcome)throws Exception;

//    지출 내용 수정하기
    int expenseUpdate(int expenseLogSeq) throws Exception;

//    지출 내용 삭제하기
    int expenseDelete(int expenseLogSeq)throws Exception;
}
