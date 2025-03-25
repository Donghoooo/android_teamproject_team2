package bitc.fullstack503.moneylog_spring.controller;

import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import bitc.fullstack503.moneylog_spring.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @PostMapping("income/process")
    public void incomeProcess (@RequestBody IncomeLogDTO income) throws Exception
    {
        income.setMemberId ("test1");
        incomeService.income (income);
    }
}
