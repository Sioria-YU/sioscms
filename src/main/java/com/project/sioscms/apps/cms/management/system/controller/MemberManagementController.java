package com.project.sioscms.apps.cms.management.system.controller;

import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.cms.management.system.domain.dto.RequestDto;
import com.project.sioscms.apps.cms.management.system.service.MemberManagementService;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cms/member")
public class MemberManagementController {

    private final MemberManagementService memberManagementService;

    @RequestMapping("/admin-list")
    public ModelAndView adminList(RequestDto requestDto) throws Exception{
        ModelAndView mav = new ModelAndView("cms/member/adminList");

        SiosPage<AccountDto.Response> siosPage = memberManagementService.getAdminList(requestDto);
        if(!siosPage.isEmpty()) {
            mav.addObject("resultList", siosPage.getContents());
            mav.addObject("pageInfo", siosPage.getPageInfo());
        }

        return mav;
    }

    @RequestMapping("/user-list")
    public ModelAndView userList(RequestDto requestDto) throws Exception{
        ModelAndView mav = new ModelAndView("cms/member/userList");
        SiosPage<AccountDto.Response> siosPage = memberManagementService.getUserList(requestDto);
        if(!siosPage.isEmpty()) {
            mav.addObject("resultList", siosPage.getContents());
            mav.addObject("pageInfo", siosPage.getPageInfo());
        }

        return mav;
    }
}
