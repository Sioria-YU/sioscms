package com.project.sioscms.apps.cms.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cms/auth")
public class CmsAuthController {

    @RequestMapping("/login")
    public String login(){
        return "cms/auth/login";
    }

    @RequestMapping("/login-proc")
    public String loginProc(){

        return "cms/auth/login";
    }
}
