package bitc.fullstack503.moneylog_spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DailySummaryDTO {
  @JsonProperty("memberId")
  private String memberId;
  @JsonProperty("year")
  private int year;
  @JsonProperty("month")
  private int month;
  @JsonProperty("day")
  private int day;
  @JsonProperty("totalIncome")
  private int totalIncome;
  @JsonProperty("totalExpense")
  private int totalExpense;
  private List<SearchDTO> transactions = new ArrayList<>(); // 리스트를 미리 초기화

  // 기본 생성자 추가
  public DailySummaryDTO() {
    this.transactions = new ArrayList<>();
  }

  public DailySummaryDTO(String memberId, int year, int month, int day, int totalIncome, int totalExpense, List<SearchDTO> transactions) {
    this.memberId = memberId;
    this.year = year;
    this.month = month;
    this.day = day;
    this.totalIncome = totalIncome;
    this.totalExpense = totalExpense;
    this.transactions = transactions != null ? transactions : new ArrayList<>(); // null 방지
  }
}
