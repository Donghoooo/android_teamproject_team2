package bitc.fullstack503.moneylog_spring.controller;

import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import bitc.fullstack503.moneylog_spring.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

//    수입 내용 입력하기
    @PostMapping("income/process")
    public void incomeProcess (@RequestBody IncomeLogDTO income) throws Exception
    {
        income.setMemberId ("test1");
        incomeService.income (income);
    }

//    수입 내용 수정하기
@PostMapping("income/update")
public int incomeUpdate(@RequestBody IncomeLogDTO incomeLog) throws Exception {
    return incomeService.incomeUpdate(incomeLog);
}

//    수입 내용 삭제하기
    @DeleteMapping("income/delete")
    public int incomeDelete(@RequestParam("incomeLogSeq") int incomeLogSeq) throws Exception{
        return incomeService.incomeDelete(incomeLogSeq);
    }
}
