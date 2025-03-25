package bitc.fullstack503.moneylog_spring.controller;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.service.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class OutcomeController {


    @Autowired
    private OutcomeService outcomeService;

    @PostMapping("outcome/process")
    public void expenseProcess (@RequestBody ExpenseLogDTO outcome) throws Exception
    {
        outcome.setMemberId ("test1");
        outcomeService.expense (outcome);
    }
}
