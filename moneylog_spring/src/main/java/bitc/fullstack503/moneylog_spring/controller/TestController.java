package bitc.fullstack503.moneylog_spring.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class TestController
{
  @GetMapping ({"/", ""})
  @ResponseBody
  public String home () throws Exception
  {
    return "index";
  }
}
