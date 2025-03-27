package bitc.fullstack503.moneylog_spring.service;
import bitc.fullstack503.moneylog_spring.dto.MainListDTO;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;

import java.util.List;
public interface MainService
{
  List<MainListDTO> mainList (MemberDTO memberDTO) throws Exception;
}
