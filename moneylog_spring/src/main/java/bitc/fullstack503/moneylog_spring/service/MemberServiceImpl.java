package bitc.fullstack503.moneylog_spring.service;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;
import bitc.fullstack503.moneylog_spring.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class MemberServiceImpl implements MemberService
{
  @Autowired
  private MemberMapper memberMapper;
  
  @Override
  public void signUp (MemberDTO member) throws Exception
  {
    memberMapper.signUp (member);
  }
  
  @Override
  public int isMemberId (String memberId) throws Exception
  {
    int result = memberMapper.isMemberId (memberId);
    if (result > 0)
    {
      return 1;
    }
    return 0;
  }
}
