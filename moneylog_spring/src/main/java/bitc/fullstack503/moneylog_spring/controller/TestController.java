package bitc.fullstack503.moneylog_spring.controller;
import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;
import bitc.fullstack503.moneylog_spring.dto.SearchDTO;
import bitc.fullstack503.moneylog_spring.service.IncomeService;
import bitc.fullstack503.moneylog_spring.service.MemberService;
import bitc.fullstack503.moneylog_spring.service.OutcomeService;
import bitc.fullstack503.moneylog_spring.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController
{
  @Autowired
  private MemberService memberService;

  @Autowired
  private IncomeService incomeService;

  @Autowired
  private OutcomeService outcomeService;

//  검색 페이지
  @Autowired
  private SearchService searchService;
  
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

//  검색 페이지
  @GetMapping("search/process")
  public List<SearchDTO> getSearchList (
      @RequestParam(required = false) String category,
      @RequestParam(required = false) String source,
      @RequestParam String startDate,
      @RequestParam String endDate) throws Exception{

    // NULL 값이 들어오면 빈 문자열("")로 처리
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("category", (category != null) ? category : "");
    paramMap.put("source", (source != null) ? source : "");

//    날짜 형식 변경
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    paramMap.put("startDate", dateFormat.parse(startDate));
    paramMap.put("endDate", dateFormat.parse(endDate));

    return searchService.getSearchList(paramMap);
  }
}
