package com.project.sioscms.cms.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cms/auth")
public class CmsAuthController {

    @RequestMapping("/login")
    public String login(){
        return "cms/auth/login";
    }

    /*@PostMapping("/login-proc")
    public String loginProc(MemberLoginDto dto){
        System.out.println("MemberLoginDto.userId ::: " + dto.getUserId());
        System.out.println("MemberLoginDto.userPw ::: " + dto.getUserPw());


        return "cms/auth/login";
    }*/
}
