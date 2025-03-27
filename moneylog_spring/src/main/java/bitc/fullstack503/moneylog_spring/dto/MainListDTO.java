package bitc.fullstack503.moneylog_spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MainListDTO {
  @JsonProperty("cate")
  private String cate;

  @JsonProperty ("usee")
  private String usee;

  @JsonProperty ("way")
  private String way;

  @JsonProperty ("amount")
  private int amount;

  @JsonProperty ("transactionDate")
  private LocalDateTime transactionDate;

  @JsonProperty ("type")
  private String type;
}