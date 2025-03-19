package bitc.fullstack503.moneylog_spring.dto;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ExpenseLogDTO
{
  private int expenseLogSeq;
  private String memberId;
  private int expense;
  private LocalDateTime expenseDate;
  private String expenseCate;
  private String memo;
  private String paymentOption;
}
