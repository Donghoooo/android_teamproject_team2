package bitc.fullstack503.moneylog_spring.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class MemberDTO
{
  @JsonProperty ("memberSeq")
  private int memberSeq;
  @JsonProperty ("memberId")
  private String memberId;
  @JsonProperty ("memberPw")
  private String memberPw;
  @JsonProperty ("memberName")
  private String memberName;
  @JsonProperty ("createDate")
  private LocalDateTime createDate;
  @JsonProperty ("updateDate")
  private LocalDateTime updateDate;
  @JsonProperty ("memberEmail")
  private String memberEmail;
}
