package com.project.sioscms.cms.management.system.controller;

import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.cms.management.system.domain.dto.MemberSearchDto;
import com.project.sioscms.cms.management.system.service.MemberManagementService;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cms/member")
public class MemberManagementController {

    private final MemberManagementService memberManagementService;

    //region ADMIN

    /**
     * 관리자 계정 목록 조회
     *
     * @param requestDto MemberSearchDto
     * @return ModelAndView resultList,pageInfo
     * @throws Exception
     */
    @RequestMapping("/admin-list")
    public ModelAndView adminList(MemberSearchDto requestDto) throws Exception {
        ModelAndView mav = new ModelAndView("cms/member/adminList");
        requestDto.setPageSize(5);

        SiosPage<AccountDto.Response> siosPage = memberManagementService.getAdminList(requestDto);
        if (!siosPage.isEmpty()) {
            mav.addObject("resultList", siosPage.getContents());
            mav.addObject("pageInfo", siosPage.getPageInfo());
        }
        if (requestDto.getMsg() != null && !requestDto.getMsg().isEmpty()) {
            mav.addObject("msg", requestDto.getMsg());
        }

        return mav;
    }

    /**
     * 관리자 계정 상세 조회
     *
     * @param id 계정 PK
     * @return ModelAndView result
     * @throws Exception
     */
    @RequestMapping("/admin-view/{id}")
    public ModelAndView adminView(@PathVariable long id) throws Exception {
        ModelAndView mav = new ModelAndView("cms/member/adminView");
        AccountDto.Response result = memberManagementService.getAdmin(id);
        if (result != null) {
            mav.addObject("result", result);
        }

        return mav;
    }

    /**
     * 관리자 계정 생성 페이지 이동
     *
     * @return String url
     */
    @RequestMapping("/admin-regist")
    public String adminRegist() {
        return "cms/member/adminReg";
    }

    /**
     * 관리자 계성 생성
     *
     * @param dto AccountDto.Request
     * @return ModelAndView url, message
     * @throws Exception
     */
    @RequestMapping(value = "/admin-save")
    public ModelAndView adminSave(AccountDto.Request dto) {
        AccountDto.Response accountDto = memberManagementService.saveAdmin(dto);

        RedirectView rv = new RedirectView("/cms/member/admin-list");
        if (accountDto != null) {
            rv.addStaticAttribute("msg", "정상 처리되었습니다.");
        } else {
            rv.addStaticAttribute("msg", "처리 실패하였습니다.");
        }
        ModelAndView mav = new ModelAndView(rv);
        return mav;

    }

    /**
     * 관리자 계정 수정
     *
     * @param dto AccountDto.Request
     * @return ModelAndView url, message
     * @throws Exception
     */
    @RequestMapping("/admin-update")
    public ModelAndView adminUpdate(AccountDto.Request dto) {
        AccountDto.Response accountDto = memberManagementService.modifyAdmin(dto);

        RedirectView rv = new RedirectView("/cms/member/admin-list");
        if (accountDto != null) {
            rv.addStaticAttribute("msg", "정상 처리되었습니다.");
        } else {
            rv.addStaticAttribute("msg", "처리 실패하였습니다.");
        }
        ModelAndView mav = new ModelAndView(rv);
        return mav;
    }

    //endregion

    //region USER

    /**
     * 사용자 계정 목록 조회
     *
     * @param requestDto MemberSearchDto
     * @return ModelAndView resultList, pageInfo
     * @throws Exception
     */
    @RequestMapping("/user-list")
    public ModelAndView userList(MemberSearchDto requestDto) throws Exception {
        ModelAndView mav = new ModelAndView("cms/member/userList");
        SiosPage<AccountDto.Response> siosPage = memberManagementService.getUserList(requestDto);
        if (!siosPage.isEmpty()) {
            mav.addObject("resultList", siosPage.getContents());
            mav.addObject("pageInfo", siosPage.getPageInfo());
        }
        if (requestDto.getMsg() != null && !requestDto.getMsg().isEmpty()) {
            mav.addObject("msg", requestDto.getMsg());
        }

        return mav;
    }

    /**
     * 사용자 계정 상세 조회
     *
     * @param id 계정 PK
     * @return ModelAndView result
     * @throws Exception
     */
    @RequestMapping("/user-view/{id}")
    public ModelAndView userView(@PathVariable long id) throws Exception {
        ModelAndView mav = new ModelAndView("cms/member/userView");
        AccountDto.Response result = memberManagementService.getUser(id);
        if (result != null) {
            mav.addObject("result", result);
        }

        return mav;
    }

    /**
     * 사용자 계정 생성 페이지 이동
     *
     * @return String url
     */
    @RequestMapping("/user-regist")
    public String userRegist() {
        return "cms/member/userReg";
    }

    /**
     * 사용자 계정 생성
     *
     * @param dto AccountDto.Request
     * @return ModelAndView url, message
     * @throws Exception
     */
    @RequestMapping(value = "/user-save")
    public ModelAndView userSave(AccountDto.Request dto) {
        AccountDto.Response accountDto = memberManagementService.saveUser(dto);

        RedirectView rv = new RedirectView("/cms/member/user-list");
        if (accountDto != null) {
            rv.addStaticAttribute("msg", "정상 처리되었습니다.");
        } else {
            rv.addStaticAttribute("msg", "처리 실패하였습니다.");
        }
        ModelAndView mav = new ModelAndView(rv);
        return mav;

    }

    /**
     * 사용자 계정 수정
     *
     * @param dto AccountDto.Request
     * @return ModelAndView url, message
     * @throws Exception
     */
    @RequestMapping("/user-update")
    public ModelAndView userUpdate(AccountDto.Request dto) {
        AccountDto.Response accountDto = memberManagementService.modifyUser(dto);

        RedirectView rv = new RedirectView("/cms/member/user-list");
        if (accountDto != null) {
            rv.addStaticAttribute("msg", "정상 처리되었습니다.");
        } else {
            rv.addStaticAttribute("msg", "처리 실패하였습니다.");
        }
        ModelAndView mav = new ModelAndView(rv);
        return mav;
    }
    //endregion
}
