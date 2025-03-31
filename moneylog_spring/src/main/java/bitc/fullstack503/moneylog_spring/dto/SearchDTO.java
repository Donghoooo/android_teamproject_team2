package bitc.fullstack503.moneylog_spring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class SearchDTO {
  @JsonProperty("type")
  private String type;  // "income" 또는 "expense" 구분
  @JsonProperty("seq")
  private int seq;
  @JsonProperty("category")
  private String category;
  @JsonProperty("source")
  private String source;
  @JsonProperty("money")
  private int money;
  @JsonProperty("use")
  private String use;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date date;
  @JsonProperty("memberId")
  private String memberId;
}