package com.project.sioscms.cms.management.contents.controller;

import com.project.sioscms.apps.contents.domain.dto.ContentsDto;
import com.project.sioscms.apps.contents.domain.dto.ContentsHistoryDto;
import com.project.sioscms.apps.contents.service.ContentsHistoryService;
import com.project.sioscms.cms.management.contents.service.ContentsManagementService;
import com.project.sioscms.common.utils.common.http.HttpUtil;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import com.project.sioscms.secure.domain.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cms/contents-manage")
public class ContentsManagementController {

    private final ContentsManagementService contentsManagementService;
    private final ContentsHistoryService contentsHistoryService;

    @Auth(role = Auth.Role.ADMIN)
    @RequestMapping("/list")
    public ModelAndView list(ContentsDto.Request requestDto) {

        SiosPage<ContentsDto.Response> siosPage = contentsManagementService.getContentsList(requestDto);

        ModelAndView mav = new ModelAndView("cms/contents/list");

        if (siosPage != null && !siosPage.isEmpty()) {
            mav.addObject("resultList", siosPage.getContents());
            mav.addObject("pageInfo", siosPage.getPageInfo());
        }

        return mav;
    }

    @Auth(role = Auth.Role.ADMIN)
    @RequestMapping("/view/{id}")
    public ModelAndView view(@PathVariable("id") Long id) {
        ContentsDto.Response dto = contentsManagementService.getContents(id);
        List<ContentsHistoryDto.Response> contentsHistoryList = contentsHistoryService.getContentsHistoryList(id);

        ModelAndView mav = new ModelAndView("cms/contents/view");
        mav.addObject("result", dto);
        mav.addObject("contentsHistoryList", contentsHistoryList);

        return mav;
    }

    @Auth(role = Auth.Role.ADMIN)
    @RequestMapping("/regist")
    public ModelAndView regist() {
        ModelAndView mav = new ModelAndView("cms/contents/regist");
        return mav;
    }

    @Auth(role = Auth.Role.ADMIN)
    @RequestMapping("/save")
    public void save(HttpServletResponse response, ContentsDto.Request requestDto, @RequestPart List<MultipartFile> files) {
        ContentsDto.Response dto = contentsManagementService.saveContents(requestDto, files);
        HttpUtil.alertAndRedirect(response, "/cms/contents-manage/view/" + dto.getId(), "정상 처리 되었습니다.", null);
    }

    @Auth(role = Auth.Role.ADMIN)
    @RequestMapping("/save-new-version")
    public void saveNewVersion(HttpServletResponse response, ContentsDto.Request requestDto) {
        //히스토리 저장 후 콘텐츠 제목,내용 업데이트
        if (contentsManagementService.saveNewVersion(requestDto)) {
            HttpUtil.alertAndRedirect(response, "/cms/contents-manage/view/" + requestDto.getId(), "정상 처리 되었습니다.", null);
        } else {
            HttpUtil.alertAndRedirect(response, "/cms/contents-manage/view/" + requestDto.getId(), "처리 중 오류가 발생하였습니다.", null);
        }
    }

    @Auth(role = Auth.Role.ADMIN)
    @RequestMapping("/preview")
    public void preView(HttpServletResponse response, @RequestParam("content") String content) {
        contentsManagementService.preView(response, content);
    }

    @Auth(role = Auth.Role.ADMIN)
    @RequestMapping("/preview-history")
    public void preViewHistory(HttpServletResponse response, @RequestParam("historyId") Long historyId) {
        contentsManagementService.preViewHistory(response, historyId);
    }
}
