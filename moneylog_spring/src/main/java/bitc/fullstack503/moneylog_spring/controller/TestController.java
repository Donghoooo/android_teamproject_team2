package bitc.fullstack503.moneylog_spring.controller;
import bitc.fullstack503.moneylog_spring.dto.SearchDTO;
import bitc.fullstack503.moneylog_spring.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class TestController
{
  @Autowired
  private SearchService searchService;

  //  검색 페이지
  @GetMapping("search/process")
  public List<SearchDTO> getSearchList (
          @RequestParam String memberId,
          @RequestParam(required = false) List<String> category,
          @RequestParam(required = false) List<String> source,
          @RequestParam String startDate,
          @RequestParam String endDate,
          @RequestParam(required = false, defaultValue = "date") String sortBy,
          @RequestParam String keyword) throws Exception{

    //    날짜 형식 변경
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date parsedStartDate, parsedEndDate;

    try {
      parsedStartDate = dateFormat.parse(startDate);
      parsedEndDate = dateFormat.parse(endDate);
      // endDate를 하루의 마지막 시간(23:59:59)로 설정
      Calendar cal = Calendar.getInstance();
      cal.setTime(parsedEndDate);
      cal.set(Calendar.HOUR_OF_DAY, 23);
      cal.set(Calendar.MINUTE, 59);
      cal.set(Calendar.SECOND, 59);
      parsedEndDate = cal.getTime();
    }
    catch (ParseException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 날짜 형식입니다.");
    }

    // NULL 값이 들어오면 빈 문자열("")로 처리
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("memberId", memberId);
    paramMap.put("startDate", parsedStartDate);
    paramMap.put("endDate", parsedEndDate);
    paramMap.put("category", category);
    paramMap.put("source", source);
    paramMap.put("keyword", keyword);
    paramMap.put("sortBy", sortBy);


    return searchService.getSearchList(paramMap);
  }
}
