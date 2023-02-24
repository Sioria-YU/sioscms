package com.project.sioscms.apps.cms.management.system.controller;

import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.cms.management.system.service.MemberManagementService;
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
    public ModelAndView adminList(){
        ModelAndView mav = new ModelAndView("cms/member/adminList");

        List<AccountDto.Response> accountList = memberManagementService.getAdminList();
        mav.addObject("resultList",accountList);

        return mav;
    }
}
