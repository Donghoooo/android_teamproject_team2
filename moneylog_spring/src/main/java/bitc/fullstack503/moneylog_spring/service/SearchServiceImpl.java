package bitc.fullstack503.moneylog_spring.service;

import bitc.fullstack503.moneylog_spring.dto.SearchDTO;
import bitc.fullstack503.moneylog_spring.mapper.SearchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SearchServiceImpl implements SearchService {

  @Autowired
  private SearchMapper searchMapper;

//  @Override
//  public List<SearchDTO> getSearchList(String category, String source, Date start, Date end) {
//    return searchMapper.getSearchList(category, source, start, end);
//  }

  @Override
  public List<SearchDTO> getSearchList(Map<String, Object> paramMap) {
    return searchMapper.getSearchList(paramMap);
  }
}

