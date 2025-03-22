package bitc.fullstack503.moneylog_spring.service;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;
public interface MemberService
{
  void signUp (MemberDTO member) throws Exception;
}
