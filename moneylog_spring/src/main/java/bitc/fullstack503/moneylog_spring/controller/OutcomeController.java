package bitc.fullstack503.moneylog_spring.controller;

import bitc.fullstack503.moneylog_spring.service.OutcomeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/outcome")
public class OutcomeController {

  private final OutcomeService outcomeService;

  // 생성자
  public OutcomeController(OutcomeService outcomeService) {
    this.outcomeService = outcomeService;
  }

//  // 특정 날짜에 대한 수입과 지출을 조회하는 엔드포인트
//  @GetMapping("/date/{date}")
//  public List<ExpenseDataDTO> getIncomeAndExpenseByDate(@PathVariable String date) {
//    return outcomeService.getIncomeAndExpenseByDate(date);
//  }
//
//  // 특정 월에 대한 수입과 지출을 조회하는 엔드포인트
//  @GetMapping("/month/{year}/{month}")
//  public List<ExpenseDataDTO> getIncomeAndExpenseByMonth(
//      @PathVariable int year,
//      @PathVariable int month) {
//    return outcomeService.getIncomeAndExpenseByMonth(year, month);
//  }
}
