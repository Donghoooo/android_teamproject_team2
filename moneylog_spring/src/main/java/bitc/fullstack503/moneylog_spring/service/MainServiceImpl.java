package bitc.fullstack503.moneylog_spring.service;
import bitc.fullstack503.moneylog_spring.dto.MainListDTO;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;
import bitc.fullstack503.moneylog_spring.mapper.MainListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MainServiceImpl implements MainService
{
  @Autowired
  private MainListMapper mainListMapper;
  
  @Override
  public List<MainListDTO> mainList (MemberDTO memberDTO) throws Exception
  {
    return mainListMapper.mainList (memberDTO);
  }
}
