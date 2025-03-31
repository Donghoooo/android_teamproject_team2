package bitc.fullstack503.moneylog_spring.service;

import bitc.fullstack503.moneylog_spring.dto.DailySummaryDTO;
import bitc.fullstack503.moneylog_spring.dto.MonthlySummaryDTO;

public interface MainService {
  // 월별 총 수입/지출 조회
  MonthlySummaryDTO getMonthlySummary(int year, int month);

  // 날짜별 수입/지출 상세 조회
  DailySummaryDTO getDailySummary(int year, int month, int day);
}
