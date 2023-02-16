package com.project.sioscms.apps.member.controller;

import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.apps.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/site/member")
@RequiredArgsConstructor
public class MemberController {

    private final AccountService accountService;

    @RequestMapping("/join/member-join")
    public String memberJoin(){
        return "site/member/join/memberJoin";
    }

    @RequestMapping("/join/member-join-proc")
    public ModelAndView memberJoinProc(AccountDto.Request dto){

        Account account = accountService.saveUser(dto);

        ModelAndView mav = new ModelAndView();
        if(account != null) {
            mav.addObject("accountDto", account.toResponse());
            mav.addObject("message","회원가입이 완료되었습니다.");
        }else{
            mav.addObject("message","회원가입에 실패하였습니다.");
        }
        mav.setViewName("main/main");
        return mav;
    }
}
