package bitc.fullstack503.moneylog_spring.dto;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class IncomeLogDTO
{
  private int incomeLogSeq;
  private String memberId;
  private int income;
  private LocalDateTime incomeDate;
  private String incomeCate;
  private String memo;
}
