package bitc.fullstack503.moneylog_spring.mapper;
import bitc.fullstack503.moneylog_spring.dto.MainListDTO;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MainListMapper
{
  List<MainListDTO> mainList (MemberDTO memberDTO) throws Exception;
}
