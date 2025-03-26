package bitc.fullstack503.moneylog_spring.mapper;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OutcomeMapper {
<<<<<<< HEAD
    // 수입과 지출을 저장하는 메서드
    void expense(ExpenseLogDTO outcome) throws Exception;

//    // 특정 날짜에 대한 수입과 지출을 가져오는 메서드
//    @Select("SELECT expense_date AS date, COALESCE(SUM(income), 0) AS income, COALESCE(SUM(expense), 0) AS expense " +
//        "FROM expense_log WHERE expense_date = #{date} GROUP BY expense_date")
//    List<ExpenseDataDTO> selectIncomeAndExpenseByDate(String date);
//
//    // 특정 월에 대한 날짜별 수입과 지출을 가져오는 메서드
//    @Select("SELECT expense_date AS date, COALESCE(SUM(income), 0) AS income, COALESCE(SUM(expense), 0) AS expense " +
//        "FROM expense_log WHERE YEAR(expense_date) = #{year} AND MONTH(expense_date) = #{month} " +
//        "GROUP BY expense_date")
//    List<ExpenseDataDTO> selectIncomeAndExpenseByMonth(int year, int month);
=======
//     지출 내용 입력하기
    public void expense(ExpenseLogDTO outcome)throws Exception;

//    지출 내용 수정하기
    int expenseUpdate(ExpenseLogDTO outcomeLog)throws Exception;

//    지출 내용 삭제하기
    int expenseDelete(int expenseLogSeq)throws Exception;

>>>>>>> origin/khamro1
}
