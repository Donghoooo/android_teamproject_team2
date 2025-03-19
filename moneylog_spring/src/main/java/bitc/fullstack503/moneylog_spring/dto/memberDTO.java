package bitc.fullstack503.moneylog_spring.dto;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class memberDTO
{
  private int memberSeq;
  private String memberId;
  private String memberPassword;
  private String memberName;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;
  private String memberEmail;
}
