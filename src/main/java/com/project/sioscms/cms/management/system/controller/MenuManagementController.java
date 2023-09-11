package com.project.sioscms.cms.management.system.controller;

import com.project.sioscms.secure.domain.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cms/menu")
public class MenuManagementController {

    @Auth(role = Auth.Role.ADMIN)
    @RequestMapping("/list")
    public ModelAndView menuList(){
        ModelAndView mav = new ModelAndView("cms/menu/list");
        return mav;
    }
}
