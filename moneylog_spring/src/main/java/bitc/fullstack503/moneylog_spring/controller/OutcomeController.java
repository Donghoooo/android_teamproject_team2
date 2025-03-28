package bitc.fullstack503.moneylog_spring.controller;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.service.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @DeleteMapping("outcome/delete")
    public int expenseDelete (@RequestParam("expenseLogSeq")int expenseLogSeq)throws Exception{
        System.out.println("expenseLogSeq : "+expenseLogSeq);
        return outcomeService.expenseDelete(expenseLogSeq);
    }
}
