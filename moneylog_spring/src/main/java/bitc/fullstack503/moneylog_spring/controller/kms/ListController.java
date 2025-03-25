package bitc.fullstack503.moneylog_spring.controller.kms;

import bitc.fullstack503.moneylog_spring.dto.IncomeLogDTO;
import bitc.fullstack503.moneylog_spring.service.kms.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class ListController {

    @Autowired
    private ListService listService;

    @GetMapping({"/list"})
    public ModelAndView list() throws Exception{
        ModelAndView mav = new ModelAndView("main/kms/list");

        List<IncomeLogDTO> incomeList = listService.selectincomeList();
        mav.addObject("incomeList", incomeList);

        return mav;
    }
}
