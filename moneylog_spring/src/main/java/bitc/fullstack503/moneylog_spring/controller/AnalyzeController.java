package bitc.fullstack503.moneylog_spring.controller;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import bitc.fullstack503.moneylog_spring.service.AnalyzeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/api/analyze1")
public class AnalyzeController {

    @Autowired
    private AnalyzeService analyzeService;

    @GetMapping({"/analyze"})
    public List<IncomeLogDTO> analyze() throws Exception {
//        ModelAndView mav = new ModelAndView("main/analyze");
        List<IncomeLogDTO> analyzeList = analyzeService.selectincomeList();
//        mav.addObject("analyzeList", analyzeList);
        return analyzeList;
    }


    @GetMapping({"/analyze1"})
    public List<ExpenseLogDTO> analyze1() throws Exception {
        List<ExpenseLogDTO> analyzeList1 = analyzeService.selectexpenseList();
        return analyzeList1;
    }



}
