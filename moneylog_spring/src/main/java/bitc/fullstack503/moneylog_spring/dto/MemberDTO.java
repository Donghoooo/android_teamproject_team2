package bitc.fullstack503.moneylog_spring.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
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
  
  public int getMemberSeq ()
  {
    return memberSeq;
  }
  
  public void setMemberSeq (int memberSeq)
  {
    this.memberSeq = memberSeq;
  }
  
  public String getMemberId ()
  {
    return memberId;
  }
  
  public void setMemberId (String memberId)
  {
    this.memberId = memberId;
  }
  
  public String getMemberPw ()
  {
    return memberPw;
  }
  
  public void setMemberPw (String memberPw)
  {
    this.memberPw = memberPw;
  }
  
  public String getMemberName ()
  {
    return memberName;
  }
  
  public void setMemberName (String memberName)
  {
    this.memberName = memberName;
  }
  
  public LocalDateTime getCreateDate ()
  {
    return createDate;
  }
  
  public void setCreateDate (LocalDateTime createDate)
  {
    this.createDate = createDate;
  }
  
  public LocalDateTime getUpdateDate ()
  {
    return updateDate;
  }
  
  public void setUpdateDate (LocalDateTime updateDate)
  {
    this.updateDate = updateDate;
  }
  
  public String getMemberEmail ()
  {
    return memberEmail;
  }
  
  public void setMemberEmail (String memberEmail)
  {
    this.memberEmail = memberEmail;
  }
}
