package bitc.fullstack503.moneylog_spring.mapper;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface MemberMapper
{
  void signUp (MemberDTO member) throws Exception;
  
  MemberDTO isMember (MemberDTO member) throws Exception;
  
  int isMemberId (String memberId) throws Exception;
  
  int isMemberName (String memberName) throws Exception;
  
  void memberDelete (String memberId) throws Exception;
  
  void memberUpdate (MemberDTO member) throws Exception;
}
