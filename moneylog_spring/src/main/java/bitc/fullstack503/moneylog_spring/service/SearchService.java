package bitc.fullstack503.moneylog_spring.service;

import bitc.fullstack503.moneylog_spring.dto.SearchDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SearchService {

//  List<SearchDTO> getSearchList(String category, String source, Date start, Date end);

  List<SearchDTO> getSearchList(Map<String, Object> paramMap);
}
