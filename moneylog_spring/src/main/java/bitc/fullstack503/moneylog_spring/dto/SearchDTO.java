package bitc.fullstack503.moneylog_spring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class SearchDTO {
  private String type;  // "income" 또는 "expense" 구분
  private int seq;
  private String category;
  private String source;
  private int money;
  private String use;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date date;
  private String memberId;
}