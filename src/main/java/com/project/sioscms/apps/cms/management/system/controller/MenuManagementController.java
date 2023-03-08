package com.project.sioscms.apps.cms.management.system.controller;

import com.project.sioscms.apps.cms.management.system.service.MenuManagementService;
import com.project.sioscms.apps.menu.domain.dto.MenuDto;
import com.project.sioscms.common.domain.dto.CommonSearchDto;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cms/menu")
public class MenuManagementController {

    private final MenuManagementService menuManagementService;

    @RequestMapping("/list")
    public ModelAndView menuList(CommonSearchDto commonSearchDto){
        ModelAndView mav = new ModelAndView("cms/menu/list");

        /*SiosPage<MenuDto.Response> siosPage = menuManagementService.getMenuList(commonSearchDto);
        if(!siosPage.isEmpty()) {
            mav.addObject("resultList", siosPage.getContents());
            mav.addObject("pageInfo", siosPage.getPageInfo());
        }*/
        return mav;
    }

    @RequestMapping("/save-menu")
    public ModelAndView saveMenu(CommonSearchDto commonSearchDto, MenuDto.Request request) throws Exception{
        ModelAndView mav = new ModelAndView("redirect:/cms/menu/list");

        /*if(menuManagementService.saveMenu(request)) {
            mav.addObject("msg", "정상처리되었습니다.");
        }else{
            mav.addObject("msg", "처리 중 오류가 발생하였습니다.");
        }*/

        return mav;

    }

}
