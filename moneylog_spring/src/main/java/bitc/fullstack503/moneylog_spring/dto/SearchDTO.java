package bitc.fullstack503.moneylog_spring.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SearchDTO {
  private String type;  // "income" 또는 "expense" 구분
  private int seq;
  private String category;
  private String source;
  private int money;
//  private String use;
  private LocalDateTime date;

}