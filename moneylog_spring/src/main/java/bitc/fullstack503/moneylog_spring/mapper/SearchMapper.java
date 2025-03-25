package bitc.fullstack503.moneylog_spring.mapper;

import bitc.fullstack503.moneylog_spring.dto.SearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface SearchMapper {
//  List<SearchDTO> getSearchList (
//      @Param("category") String category, //카테고리
//      @Param("source") String source, // 지불방식
//      @Param("startDate") Date start, // 시작날짜
//      @Param("endDate") Date end  // 끝 날짜
//  );

  List<SearchDTO> getSearchList(Map<String, Object> paramMap);
}
