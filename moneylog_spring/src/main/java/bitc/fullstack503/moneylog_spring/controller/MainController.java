package bitc.fullstack503.moneylog_spring.controller;

import bitc.fullstack503.moneylog_spring.dto.DailySummaryDTO;
import bitc.fullstack503.moneylog_spring.dto.MonthlySummaryDTO;
import bitc.fullstack503.moneylog_spring.dto.SearchDTO;
import bitc.fullstack503.moneylog_spring.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

  @Autowired
  private MainService mainService;

  @GetMapping("/main/monthly")
  public MonthlySummaryDTO displayMonthlySummary(
      @RequestParam String memberId,
      @RequestParam int year,
      @RequestParam int month) throws Exception {
    MonthlySummaryDTO monthlySummary = mainService.getMonthlySummary(memberId, year, month);
    System.out.println("Member ID: " + memberId);
    System.out.println("Total Income: " + monthlySummary.getTotalIncome());
    System.out.println("Total Expense: " + monthlySummary.getTotalExpense());

    for (DailySummaryDTO daily : monthlySummary.getDailySummary()) {
      System.out.println("Day " + daily.getDay() + ": Income = " + daily.getTotalIncome() + ", Expense = " + daily.getTotalExpense());
    }
    return monthlySummary;
  }

  @GetMapping("/main/transactions")
  public DailySummaryDTO displayDailySummary(
      @RequestParam String memberId,
      @RequestParam int year,
      @RequestParam int month,
      @RequestParam int day) throws Exception{
    DailySummaryDTO dailySummary = mainService.getDailySummary(memberId, year, month, day);
    System.out.println("Member ID: " + memberId);
    System.out.println("Total Income: " + dailySummary.getTotalIncome());
    System.out.println("Total Expense: " + dailySummary.getTotalExpense());

    for (SearchDTO item : dailySummary.getTransactions()) {
      System.out.println("Category: " + item.getCategory() + ", Money: " + item.getMoney() + " (" + item.getType() + ")");
    }
    return dailySummary;
  }
}
