package bitc.fullstack503.moneylog_spring.controller;
import bitc.fullstack503.moneylog_spring.dto.MainListDTO;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;
import bitc.fullstack503.moneylog_spring.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
@RestController
public class MainController
{
  @Autowired
  private MainService mainService;
  
  @PostMapping ("mainList")
  public List<MainListDTO> mainList (MemberDTO memberDTO) throws Exception
  {
    LocalDateTime date = memberDTO.getCreateDate ();
    LocalDateTime endOfDay = date.toLocalDate ().atTime (LocalTime.MAX);
    memberDTO.setUpdateDate (endOfDay);
    return mainService.mainList (memberDTO);
  }
}
