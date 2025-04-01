package bitc.fullstack503.moneylog_spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MonthlySummaryDTO {
  @JsonProperty("memberId")
  private String memberId;
  @JsonProperty("totalIncome")
  private int totalIncome;
  @JsonProperty("totalExpense")
  private int totalExpense;
  private List<DailySummaryDTO> dailySummary;

  public MonthlySummaryDTO(String memberId, int totalIncome, int totalExpense, List<DailySummaryDTO> dailySummary) {
    this.memberId = memberId;
    this.totalIncome = totalIncome;
    this.totalExpense = totalExpense;
    this.dailySummary = dailySummary;
  }
}
