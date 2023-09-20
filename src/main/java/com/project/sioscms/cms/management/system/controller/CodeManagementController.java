package com.project.sioscms.cms.management.system.controller;

import com.project.sioscms.apps.code.domain.dto.CodeDto;
import com.project.sioscms.apps.code.domain.dto.CodeGroupDto;
import com.project.sioscms.cms.management.system.domain.dto.CodeSearchDto;
import com.project.sioscms.cms.management.system.service.CodeManagementService;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import com.project.sioscms.secure.domain.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/cms/code")
@RequiredArgsConstructor
public class CodeManagementController {

    private final CodeManagementService codeManagementService;

    @Auth(role = Auth.Role.ADMIN)
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

    @Auth(role = Auth.Role.ADMIN)
    @RequestMapping("/code-group/view/{codeGroupId}")
    public ModelAndView codeGroupView(@PathVariable(value = "codeGroupId") String codeGroupId){
        ModelAndView mav = new ModelAndView("cms/code/codeGroupView");

        if (ObjectUtils.isEmpty(codeGroupId)){
            mav.setViewName("cms/code/codeGroupList");
            mav.addObject("msg","잘못 된 페이지입니다.");
            return mav;
        }

        CodeGroupDto.Response codeGroupInfo = codeManagementService.getCodeGroup(codeGroupId);
        mav.addObject("codeGroupInfo", codeGroupInfo);

        List<CodeDto.Response> codeList = codeManagementService.getCodeList(codeGroupId);
        mav.addObject("codeList", codeList);

        return mav;
    }

}
