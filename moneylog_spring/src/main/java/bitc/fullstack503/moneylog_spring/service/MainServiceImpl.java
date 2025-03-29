package bitc.fullstack503.moneylog_spring.service;
import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
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
  public List<IncomeLogDTO> selectIncomeMain(String year, String month, String monthPlus, String memberId) throws Exception {
    return mainListMapper.selectIncomeMain(year, month, monthPlus, memberId);
  }

  @Override
  public List<ExpenseLogDTO> selectExpenseMain(String year, String month, String monthPlus, String memberId) throws Exception {
    return mainListMapper.selectExpenseMain(year, month, monthPlus, memberId);
  }


//  @Override
//  public List<MainDTO> mainList (MemberDTO memberDTO) throws Exception
//  {
//    return mainListMapper.mainList (memberDTO);
//  }




}
