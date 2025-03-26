package bitc.fullstack503.moneylog_spring.controller;
import bitc.fullstack503.moneylog_spring.dto.SearchDTO;
import bitc.fullstack503.moneylog_spring.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController
{
//  검색 페이지
  @Autowired
  private SearchService searchService;
  
  @GetMapping ({"/", ""})
  public String home () throws Exception
  {
    return "index";
  }


//  검색 페이지
  @GetMapping("search/process")
  public List<SearchDTO> getSearchList (
      @RequestParam(required = false) String category,
      @RequestParam(required = false) String source,
      @RequestParam String startDate,
      @RequestParam String endDate) throws Exception{

    // NULL 값이 들어오면 빈 문자열("")로 처리
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("category", (category != null) ? category : "");
    paramMap.put("source", (source != null) ? source : "");

//    날짜 형식 변경
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    paramMap.put("startDate", dateFormat.parse(startDate));
    paramMap.put("endDate", dateFormat.parse(endDate));

    return searchService.getSearchList(paramMap);
  }
}
