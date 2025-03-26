package bitc.fullstack503.moneylog_spring.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ExpenseLogDTO
{
  @JsonProperty ("expenseLogSeq")
  private int expenseLogSeq;
  @JsonProperty ("memberId")
  private String memberId;
  @JsonProperty ("expenseMoney")
  private int expenseMoney;
  @JsonProperty ("expenseDate")
  private LocalDateTime expenseDate;
  @JsonProperty ("expenseCate")
  private String expenseCate;
  @JsonProperty ("expenseMemo")
  private String expenseMemo;
  @JsonProperty ("paymentOption")
  private String paymentOption;
  @JsonProperty ("expenseUse")
  private String expenseUse;
}
