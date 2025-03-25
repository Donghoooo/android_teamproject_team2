package bitc.fullstack503.moneylog_spring.mapper;

import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IncomeMapper {
    public void income(IncomeLogDTO income)throws Exception;


}
