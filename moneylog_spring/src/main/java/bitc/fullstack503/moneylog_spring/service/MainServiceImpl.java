package bitc.fullstack503.moneylog_spring.service;

import bitc.fullstack503.moneylog_spring.dto.DailySummaryDTO;
import bitc.fullstack503.moneylog_spring.dto.MonthlySummaryDTO;
import bitc.fullstack503.moneylog_spring.dto.SearchDTO;
import bitc.fullstack503.moneylog_spring.mapper.MainMapper;
import bitc.fullstack503.moneylog_spring.mapper.SearchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Service
public class MainServiceImpl implements MainService {

  @Autowired
  private MainMapper mainMapper;

  @Transactional
  @Override
  public MonthlySummaryDTO getMonthlySummary(String memberId, int year, int month) {
    // 해당 월의 수입과 지출 합계 계산
    int totalIncome = mainMapper.getTotalIncome(memberId, year, month);
    int totalExpense = mainMapper.getTotalExpense(memberId,year, month);

    // 해당 월의 날짜별 수입/지출 데이터 가져오기
    List<DailySummaryDTO> dailySummary = mainMapper.getDailySummary(memberId, year, month);

    return new MonthlySummaryDTO(memberId, totalIncome, totalExpense, dailySummary);
  }

  @Transactional
  @Override
  public DailySummaryDTO getDailySummary(String memberId, int year, int month, int day) {
    // 해당 날짜의 수입과 지출 합계 계산
    int totalIncome = mainMapper.getTotalIncomeForDay(memberId, year, month, day);
    int totalExpense = mainMapper.getTotalExpenseForDay(memberId, year, month, day);

    // 해당 날짜의 수입/지출 목록 가져오기
    List<SearchDTO> transactions = mainMapper.getTransactionsForDay(memberId, year, month, day);

    // 로그 추가
    System.out.println("DB에서 가져온 특정 날짜 총 수입: " + totalIncome);
    System.out.println("DB에서 가져온 특정 날짜 총 지출: " + totalExpense);
    System.out.println("DB에서 가져온 특정 날짜 거래 내역 개수: " + (transactions != null ? transactions.size() : 0));

    if (transactions == null) {
      transactions = new ArrayList<>();
    }

    return new DailySummaryDTO(memberId, year, month, day, totalIncome, totalExpense, transactions);
  }
}
