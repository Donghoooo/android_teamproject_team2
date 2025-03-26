package bitc.fullstack503.moneylog_spring.controller;

<<<<<<< HEAD
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
=======
import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.service.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class OutcomeController {


    @Autowired
    private OutcomeService outcomeService;

//    지출 내용 입력하기
    @PostMapping("outcome/process")
    public void expenseProcess (@RequestBody ExpenseLogDTO outcome) throws Exception
    {
        outcome.setMemberId ("test1");
        outcomeService.expense (outcome);
    }

//    지출 내용 수정하기
    @PostMapping("outcome/update")
    public int expenseUpdate (@RequestBody ExpenseLogDTO outcomeLog)throws Exception{
        return outcomeService.expenseUpdate(outcomeLog);
    }

//    지출 내용 삭제하기
    @PostMapping("outcome/delete")
    public int expenseDelete (@RequestParam("expenseLogSeq")int expenseLogSeq)throws Exception{
        return outcomeService.expenseDelete(expenseLogSeq);
    }
>>>>>>> origin/khamro1
}
