package bitc.fullstack503.moneylog_spring.controller;
import bitc.fullstack503.moneylog_spring.dto.MemberDTO;
import bitc.fullstack503.moneylog_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
public class MemberController
{
  @Autowired
  private MemberService memberService;
  
  @PostMapping ("logIn/Process")
  public Boolean loginProcess (@RequestBody MemberDTO member) throws Exception
  {
    return memberService.isMember (member);
  }
  
  @PostMapping ("signUp/process")
  public void signUpProcess (@RequestBody MemberDTO member) throws Exception
  {
    memberService.signUp (member);
  }
  
  @GetMapping ("isMemberId")
  public boolean isMemberId (@RequestParam ("Id") String memberId) throws Exception
  {
    return memberService.isMemberId (memberId);
  }
  
  @GetMapping ("isMemberName")
  public boolean isMemberName (@RequestParam ("Name") String memberName) throws Exception
  {
    return memberService.isMemberName (memberName);
  }
  
  @PostMapping ("memberInfo")
  public MemberDTO memberInfo (@RequestBody MemberDTO member) throws Exception
  {
    return memberService.memberInfo (member);
  }
  
  @PostMapping ("memberDelete")
  public void memberDelete (@RequestParam ("Id") String memberId) throws Exception
  {
    memberService.memberDelete (memberId);
  }
  
  @PostMapping ("memberUpdate")
  public MemberDTO memberUpdate (@RequestBody MemberDTO member) throws Exception
  {
    return memberService.memberUpdate (member);
  }
}
