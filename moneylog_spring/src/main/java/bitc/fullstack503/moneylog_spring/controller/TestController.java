package bitc.fullstack503.moneylog_spring.controller;
import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;
import bitc.fullstack503.moneylog_spring.service.IncomeService;
import bitc.fullstack503.moneylog_spring.service.MemberService;
import bitc.fullstack503.moneylog_spring.service.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class TestController
{
  @Autowired
  private MemberService memberService;

  @Autowired
  private IncomeService incomeService;

  @Autowired
  private OutcomeService outcomeService;
  
  @GetMapping ({"/", ""})
  public String home () throws Exception
  {
    return "index";
  }
  
  @PostMapping ("loginProcess")
  public String loginProcess (@RequestBody MemberDTO member) throws Exception
  {
    System.out.println (member.getMemberId ());
    System.out.println (member.getMemberPw ());
    return "login";
  }
  
  @PostMapping ("signUp/process")
  public void signUpProcess (@RequestBody MemberDTO member) throws Exception
  {
    memberService.signUp (member);
  }

  @PostMapping("income/process")
  public void incomeProcess (@RequestBody IncomeLogDTO income) throws Exception
  {
    incomeService.income(income);
  }

  @PostMapping("outcome/process")
  public void expenseProcess (@RequestBody ExpenseLogDTO outcome) throws Exception{
    outcomeService.expense(outcome);
  }

}
