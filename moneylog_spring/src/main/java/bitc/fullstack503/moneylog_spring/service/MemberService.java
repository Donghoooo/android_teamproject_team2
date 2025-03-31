package bitc.fullstack503.moneylog_spring.service;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;
public interface MemberService
{
  void signUp (MemberDTO member) throws Exception;
  
  boolean isMember (MemberDTO member) throws Exception;
  
  boolean isMemberId (String memberId) throws Exception;
  
  boolean isMemberName (String memberName) throws Exception;
  
  MemberDTO memberInfo (MemberDTO member) throws Exception;
  
  void memberDelete (String memberId) throws Exception;
  
  MemberDTO memberUpdate (MemberDTO member) throws Exception;
}
