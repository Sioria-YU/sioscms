package com.project.sioscms.apps.cms.main.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cms")
public class CmsMainController {

    @RequestMapping("/main")
    public ModelAndView main(@AuthenticationPrincipal User user){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("cms/main/cmsMain");
        mav.addObject("memberVO", user);
        return mav;
    }
}
