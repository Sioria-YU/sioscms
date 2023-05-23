package com.project.sioscms.cms.management.system.controller;

import com.project.sioscms.apps.code.domain.dto.CodeGroupDto;
import com.project.sioscms.cms.management.system.domain.dto.CodeSearchDto;
import com.project.sioscms.cms.management.system.service.CodeManagementService;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cms/code")
@RequiredArgsConstructor
public class CodeManagementController {

    private final CodeManagementService codeManagementService;

    @RequestMapping("/code-group/list")
    public ModelAndView codeGrouplist(CodeSearchDto dto) throws Exception{
        ModelAndView mav = new ModelAndView("cms/code/codeGroupList");

        SiosPage<CodeGroupDto.Response> siosPage = codeManagementService.getCodeGroupList(dto);
        if (!siosPage.isEmpty()) {
            mav.addObject("resultList", siosPage.getContents());
            mav.addObject("pageInfo", siosPage.getPageInfo());
        }

        return mav;
    }
}
