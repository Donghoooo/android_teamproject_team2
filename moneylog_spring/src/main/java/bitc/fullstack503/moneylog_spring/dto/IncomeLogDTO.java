package bitc.fullstack503.moneylog_spring.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class IncomeLogDTO
{
  @JsonProperty ("incomeLogSeq")
  private int incomeLogSeq;
  @JsonProperty ("memberId")
  private String memberId;
  @JsonProperty ("incomeMoney")
  private int incomeMoney;
  @JsonProperty ("incomeDate")
  private String incomeDate;
  @JsonProperty ("incomeCate")
  private String incomeCate;
  @JsonProperty ("incomeMemo")
  private String incomeMemo;
  @JsonProperty ("incomeSource")
  private String incomeSource;
  @JsonProperty ("incomeUse")
  private String incomeUse;
}
