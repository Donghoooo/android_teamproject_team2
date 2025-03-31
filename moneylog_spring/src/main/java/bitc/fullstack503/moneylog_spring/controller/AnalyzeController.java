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
    public List<IncomeLogDTO> analyze(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("memberId") String memberId) throws Exception {

        System.out.println("startDate: " + startDate + " endDate: " + endDate);
        List<IncomeLogDTO> analyzeList = analyzeService.selectincomeList(startDate, endDate, memberId);
        return analyzeList;
    }


    @GetMapping({"/analyze1"})
    public List<ExpenseLogDTO> analyze1(String startDate, String endDate, String memberId) throws Exception {

        if (startDate == null) {
            startDate = "2025-01-01";
        }

        if (endDate == null) {
            endDate = "2025-12-31";
        }

        List<ExpenseLogDTO> analyzeList1 = analyzeService.selectexpenseList(startDate, endDate, memberId);
        return analyzeList1;
    }



}
