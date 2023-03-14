package com.project.sioscms.apps.cms.management.system.controller;

import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.cms.management.system.domain.dto.MemberSearchDto;
import com.project.sioscms.apps.cms.management.system.service.MemberManagementService;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cms/member")
public class MemberManagementController {

    private final MemberManagementService memberManagementService;

    //region ADMIN
    @RequestMapping("/admin-list")
    public ModelAndView adminList(MemberSearchDto requestDto) throws Exception{
        ModelAndView mav = new ModelAndView("cms/member/adminList");
        requestDto.setPageSize(5);

        SiosPage<AccountDto.Response> siosPage = memberManagementService.getAdminList(requestDto);
        if(!siosPage.isEmpty()) {
            mav.addObject("resultList", siosPage.getContents());
            mav.addObject("pageInfo", siosPage.getPageInfo());
        }
        if(requestDto.getMsg() != null && !requestDto.getMsg().isEmpty()){
            mav.addObject("msg", requestDto.getMsg());
        }

        return mav;
    }

    @RequestMapping("/admin-view/{id}")
    public ModelAndView adminView(@PathVariable long id) throws Exception {
        ModelAndView mav = new ModelAndView("cms/member/adminView");
        AccountDto.Response result = memberManagementService.getAdmin(id);
        if(result != null){
            mav.addObject("result", result);
        }

        return mav;
    }

    @RequestMapping("/admin-regist")
    public String adminRegist(){
        return "cms/member/adminReg";
    }

    @RequestMapping(value = "/admin-save")
    public ModelAndView adminSave(AccountDto.Request dto, RedirectAttributes redirectAttributes) throws Exception{
        AccountDto.Response accountDto = memberManagementService.saveAdmin(dto);

        RedirectView rv = new RedirectView("/cms/member/admin-list");
        if(accountDto != null) {
            rv.addStaticAttribute("msg","정상 처리되었습니다.");
        }else{
            rv.addStaticAttribute("msg","회원가입에 실패하였습니다.");
        }
        ModelAndView mav = new ModelAndView(rv);
        return mav;

    }

    //endregion

    //region USER
    @RequestMapping("/user-list")
    public ModelAndView userList(MemberSearchDto requestDto) throws Exception{
        ModelAndView mav = new ModelAndView("cms/member/userList");
        SiosPage<AccountDto.Response> siosPage = memberManagementService.getUserList(requestDto);
        if(!siosPage.isEmpty()) {
            mav.addObject("resultList", siosPage.getContents());
            mav.addObject("pageInfo", siosPage.getPageInfo());
        }
        if(requestDto.getMsg() != null && !requestDto.getMsg().isEmpty()){
            mav.addObject("msg", requestDto.getMsg());
        }

        return mav;
    }

    //endregion
}
