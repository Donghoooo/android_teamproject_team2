package bitc.fullstack503.moneylog_spring.controller.kms;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import bitc.fullstack503.moneylog_spring.service.kms.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ListController {

    @Autowired
    private ListService listService;

    @GetMapping({"/list/income"})
    public List<IncomeLogDTO> incomelist(String memberId) throws Exception{
//        ModelAndView mav = new ModelAndView("main/kms/list");

        System.out.println(memberId);

        List<IncomeLogDTO> incomeList = listService.selectincomeList(memberId);
//        mav.addObject("incomeList", incomeList);

        return incomeList;
    }

    @GetMapping({"/list/expense"})
    public List<ExpenseLogDTO> expenselist(String memberId) throws Exception{
//        ModelAndView mav = new ModelAndView("main/kms/list");

        List<ExpenseLogDTO> expenseList = listService.selectExpenseList(memberId);
//        mav.addObject("incomeList", incomeList);

        return expenseList;
    }

}
