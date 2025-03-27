package bitc.fullstack503.moneylog_spring.mapper;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface MemberMapper
{
  public void signUp (MemberDTO member) throws Exception;
}
