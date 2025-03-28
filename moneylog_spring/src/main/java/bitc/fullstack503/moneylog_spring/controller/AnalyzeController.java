package bitc.fullstack503.moneylog_spring.controller;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;
import bitc.fullstack503.moneylog_spring.service.AnalyzeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.List;

@RestController
//@RequestMapping("/api/analyze1")
public class AnalyzeController {

    @Autowired
    private AnalyzeService analyzeService;

    @GetMapping({"/analyze"})
    public List<IncomeLogDTO> analyze(String startDate, String endDate) throws Exception {
        System.out.println("startDate: " + startDate + " endDate: " + endDate);
        List<IncomeLogDTO> analyzeList = analyzeService.selectincomeList(startDate, endDate);
        return analyzeList;
    }


    @GetMapping({"/analyze1"})
    public List<ExpenseLogDTO> analyze1(String startDate, String endDate) throws Exception {
        List<ExpenseLogDTO> analyzeList1 = analyzeService.selectexpenseList(startDate, endDate);
        return analyzeList1;
    }



}
