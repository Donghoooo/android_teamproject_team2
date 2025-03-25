package bitc.fullstack503.moneylog_spring.mapper;

import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IncomeMapper {
//    수입 내용 입력하기
    public void income(IncomeLogDTO income)throws Exception;

//    수입 내용 수정하기
    int incomeUpdate(int incomeLogSeq) throws Exception;

//    수입 내용 삭제하기
    int incomeDelete(int incomeLogSeq)throws Exception;
}
