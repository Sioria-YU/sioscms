package com.project.sioscms.cms.main.controller;

import com.project.sioscms.secure.domain.Auth;
import com.project.sioscms.secure.domain.UserAccount;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cms")
public class CmsMainController {

    @Auth(role = Auth.Role.ADMIN)
    @RequestMapping("/main")
    public ModelAndView main(@AuthenticationPrincipal UserAccount userAccount){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("cms/main/cmsMain");
        mav.addObject("accountDto", userAccount.getAccountDto());
        return mav;
    }

}
