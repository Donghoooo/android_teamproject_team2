package bitc.fullstack503.moneylog_spring.service;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;
import bitc.fullstack503.moneylog_spring.mapper.AnalyzeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyzeServiceImpl implements AnalyzeService {
    @Autowired
    private AnalyzeMapper analyzeMapper;

    @Override
    public List<IncomeLogDTO> selectincomeList(String startDate, String endDate) throws Exception {
        return analyzeMapper.selectincomeList(startDate, endDate);
    }

    @Override
    public List<ExpenseLogDTO> selectexpenseList(String startDate, String endDate) throws Exception {
        return analyzeMapper.selectexpenseList(startDate, endDate);
    }

//    @Override
//    public List<IncomeLogDTO> selectTimeList(String timeStart, String timeEnd) throws Exception {
//        return analyzeMapper.selectTimeList();
//    }
}
