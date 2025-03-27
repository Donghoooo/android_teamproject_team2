package bitc.fullstack503.moneylog_spring.service.kms;

import bitc.fullstack503.moneylog_spring.dto.ExpenseLogDTO;
import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import bitc.fullstack503.moneylog_spring.mapper.kms.ListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListServiceImpl implements ListService {

    @Autowired
    private ListMapper listMapper;

    @Override
    public List<IncomeLogDTO> selectincomeList(String memberId) throws Exception {
        return listMapper.selectincomeList(memberId);
    }

    @Override
    public List<ExpenseLogDTO> selectExpenseList(String memberId) throws Exception {
        return listMapper.selectExpenseList(memberId);
    }
}
