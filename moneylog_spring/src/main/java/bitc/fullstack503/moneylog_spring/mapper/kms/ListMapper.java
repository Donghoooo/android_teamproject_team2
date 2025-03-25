package bitc.fullstack503.moneylog_spring.mapper.kms;

import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ListMapper {
    List<IncomeLogDTO> selectincomeList() throws Exception;
}
