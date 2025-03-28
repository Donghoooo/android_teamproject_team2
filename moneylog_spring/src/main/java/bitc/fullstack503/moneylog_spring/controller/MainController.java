package bitc.fullstack503.moneylog_spring.controller;
import bitc.fullstack503.moneylog_spring.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
public class MainController
{
  @Autowired
  private MainService mainService;

}
