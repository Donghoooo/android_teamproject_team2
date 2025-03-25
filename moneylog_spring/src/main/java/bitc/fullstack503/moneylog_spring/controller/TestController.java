package bitc.fullstack503.moneylog_spring.controller;
import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import bitc.fullstack503.moneylog_spring.service.IncomeService;
import bitc.fullstack503.moneylog_spring.service.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class TestController {

  @GetMapping ({"/", ""})
  public String home () throws Exception
  {
    return "index";
  }
  


}
