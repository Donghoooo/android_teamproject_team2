package bitc.fullstack503.moneylog_spring.mapper;
import bitc.fullstack503.moneylog_spring.dto.MainDTO;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MainMapper
{
  List<MainDTO> mainList (MemberDTO memberDTO) throws Exception;

  // 한 달 동안의 수입, 지출 총합을 가져오는 메서드
  MainDTO getMonthSum() throws Exception;


}
