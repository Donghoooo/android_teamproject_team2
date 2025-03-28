package bitc.fullstack503.moneylog_spring.service;
import bitc.fullstack503.moneylog_spring.dto.MainDTO;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;

import java.util.List;
import java.util.Map;

public interface MainService
{
  List<MainDTO> mainList (MemberDTO memberDTO) throws Exception;

}
