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

//    @GetMapping({"/main/income"})
//    public List<IncomeLogDTO> incomeMain(String year, String month, String memberId) throws Exception{
//
//        String monthPlus = month + 1;
//
//        List<IncomeLogDTO> incomeMain = listService.selectIncomeMain(year, month, monthPlus, memberId);
//
//        return incomeMain;
//    }

    @GetMapping({"/list/income"})
    public List<IncomeLogDTO> incomelist() throws Exception{
//        ModelAndView mav = new ModelAndView("main/kms/list");

        List<IncomeLogDTO> incomeList = listService.selectincomeList();
//        mav.addObject("incomeList", incomeList);

        return incomeList;
    }

    @GetMapping({"/list/expense"})
    public List<ExpenseLogDTO> expenselist() throws Exception{
//        ModelAndView mav = new ModelAndView("main/kms/list");

        List<ExpenseLogDTO> expenseList = listService.selectExpenseList();
//        mav.addObject("incomeList", incomeList);

        return expenseList;
    }

}
