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
  @JsonProperty ("expense")
  private int expense;
  @JsonProperty ("expenseDate")
  private LocalDateTime expenseDate;
  @JsonProperty ("expenseCate")
  private String expenseCate;
  @JsonProperty ("memo")
  private String memo;
  @JsonProperty ("paymentOption")
  private String paymentOption;
  @JsonProperty ("expenseUse")
  private String expenseUse;
}
