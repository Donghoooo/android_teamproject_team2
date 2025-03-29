package bitc.fullstack503.moneylog_spring.controller;
import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import bitc.fullstack503.moneylog_spring.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MainController
{
  @Autowired
  private MainService mainService;

  @GetMapping({"/main/income"})
  public List<IncomeLogDTO> incomeMain(String year, String month, String memberId) throws Exception{

    String monthPlus = String.valueOf(Integer.parseInt(month) + 1);

    List<IncomeLogDTO> incomeMain = mainService.selectIncomeMain(year, month, monthPlus, memberId);

    return incomeMain;
  }

  @GetMapping({"/main/expense"})
  public List<ExpenseLogDTO> expenseMain(String year, String month, String memberId) throws Exception{

    String monthPlus = String.valueOf(Integer.parseInt(month) + 1);

    List<ExpenseLogDTO> expenseMain = mainService.selectExpenseMain(year, month, monthPlus, memberId);

    return expenseMain;
  }

}
