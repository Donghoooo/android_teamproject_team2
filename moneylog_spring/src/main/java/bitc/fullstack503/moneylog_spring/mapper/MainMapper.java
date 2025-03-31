package bitc.fullstack503.moneylog_spring.mapper;

import bitc.fullstack503.moneylog_spring.dto.DailySummaryDTO;
import bitc.fullstack503.moneylog_spring.dto.SearchDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MainMapper {

  // 월별 수입 총합
  Integer getTotalIncome(int year, int month);

  // 월별 지출 총합
  Integer getTotalExpense(int year, int month);

  // 월별 날짜별 수입/지출 데이터

  List<DailySummaryDTO> getDailySummary(int year, int month);

  // 특정 날짜의 수입 총합
  Integer getTotalIncomeForDay(int year, int month, int day);

  // 특정 날짜의 지출 총합
  Integer getTotalExpenseForDay(int year, int month, int day);

  // 특정 날짜의 수입/지출 내역
  List<SearchDTO> getTransactionsForDay(int year, int month, int day);
}

