package com.project.sioscms.cms.management.board.controller;

import com.project.sioscms.apps.board.domain.dto.BoardMasterDto;
import com.project.sioscms.apps.board.service.BoardMasterService;
import com.project.sioscms.cms.management.board.service.BoardMasterManagementService;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cms/board")
@RequiredArgsConstructor
public class BoardMasterManagementController {

    private final BoardMasterService boardMasterService;
    private final BoardMasterManagementService boardMasterManagementService;

    @RequestMapping("/master-list")
    public ModelAndView boardMasterList(BoardMasterDto.Request requestDto){
        ModelAndView mav = new ModelAndView("cms/board/boardMasterList");
        SiosPage<BoardMasterDto.Response> siosPage = boardMasterService.getBoardMasterList(requestDto);

        if(siosPage != null && !siosPage.isEmpty()){
            mav.addObject("resultList", siosPage.getContents());
            mav.addObject("pageInfo", siosPage.getPageInfo());
        }

        //게시판 유형
        mav.addObject("boardTypeCodeList", boardMasterManagementService.getBoardTypeCodeList());
        return mav;
    }
}
