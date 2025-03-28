package bitc.fullstack503.moneylog_spring.service;
import bitc.fullstack503.moneylog_spring.dto.MainDTO;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;
import bitc.fullstack503.moneylog_spring.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainServiceImpl implements MainService
{
  @Autowired
  private MainMapper mainListMapper;


  @Override
  public List<MainDTO> mainList (MemberDTO memberDTO) throws Exception
  {
    return mainListMapper.mainList (memberDTO);
  }
}
