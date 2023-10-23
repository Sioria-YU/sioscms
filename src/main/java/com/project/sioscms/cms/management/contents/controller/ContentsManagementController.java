package com.project.sioscms.cms.management.contents.controller;

import com.project.sioscms.apps.contents.domain.dto.ContentsDto;
import com.project.sioscms.cms.management.contents.service.ContentsManagementService;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import com.project.sioscms.secure.domain.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cms/contents-manage")
public class ContentsManagementController {

    private final ContentsManagementService contentsManagementService;

    @Auth(role = Auth.Role.ADMIN)
    @RequestMapping("/list")
    public ModelAndView list(ContentsDto.Request requestDto){

        SiosPage<ContentsDto.Response> siosPage = contentsManagementService.getContentsList(requestDto);

        ModelAndView mav = new ModelAndView("cms/contents/list");

        if(siosPage != null && !siosPage.isEmpty()){
            mav.addObject("resultList", siosPage.getContents());
            mav.addObject("pageInfo", siosPage.getPageInfo());
        }

        return mav;
    }

}
