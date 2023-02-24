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
    public ModelAndView adminList(RequestDto requestDto){
        ModelAndView mav = new ModelAndView("cms/member/adminList");

        List<AccountDto.Response> accountList = memberManagementService.getAdminList(requestDto);
        mav.addObject("resultList",accountList);

        return mav;
    }

    @RequestMapping("/user-list")
    public ModelAndView userList(RequestDto requestDto){
        ModelAndView mav = new ModelAndView("cms/member/userList");
        SiosPage siosPage = memberManagementService.getUserList(requestDto);
        mav.addObject("resultList", siosPage.getPage().getContent());
        mav.addObject("pageInfo", siosPage);

        return mav;
    }
}
