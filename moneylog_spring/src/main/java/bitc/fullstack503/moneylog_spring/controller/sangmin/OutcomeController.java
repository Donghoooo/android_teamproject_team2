package bitc.fullstack503.moneylog_spring.controller.sangmin;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.service.sangmin.OutcomeService;
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
}
