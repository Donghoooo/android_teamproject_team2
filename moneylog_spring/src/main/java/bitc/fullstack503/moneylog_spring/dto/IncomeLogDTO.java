package bitc.fullstack503.moneylog_spring.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class IncomeLogDTO
{
  @JsonProperty ("incomeLogSeq")
  private int incomeLogSeq;
  @JsonProperty ("memberId")
  private String memberId;
  @JsonProperty ("income")
  private int income;
  @JsonProperty ("incomeDate")
  private LocalDateTime incomeDate;
  @JsonProperty ("incomeCate")
  private String incomeCate;
  @JsonProperty ("memo")
  private String memo;
  @JsonProperty ("incomeSource")
  private String incomeSource;
}
