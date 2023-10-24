package com.project.sioscms.cms.management.contents.controller;

import com.project.sioscms.apps.attach.domain.dto.AttachFileGroupDto;
import com.project.sioscms.apps.attach.service.AttachFileService;
import com.project.sioscms.apps.contents.domain.dto.ContentsDto;
import com.project.sioscms.cms.management.contents.service.ContentsManagementService;
import com.project.sioscms.common.utils.common.http.HttpUtil;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import com.project.sioscms.secure.domain.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private final AttachFileService attachFileService;

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

    @Auth(role = Auth.Role.ADMIN)
    @RequestMapping("/view/{id}")
    public ModelAndView view(@PathVariable("id") Long id){
        ContentsDto.Response dto = contentsManagementService.getContents(id);

        ModelAndView mav = new ModelAndView("cms/contents/view");
        mav.addObject("result", dto);

        return mav;
    }

    @Auth(role = Auth.Role.ADMIN)
    @RequestMapping("/regist")
    public ModelAndView regist(ContentsDto.Request requestDto){
        ModelAndView mav = new ModelAndView("cms/contents/regist");
        return mav;
    }

    @Auth(role = Auth.Role.ADMIN)
    @RequestMapping("/save")
    public void save(HttpServletResponse response, ContentsDto.Request requestDto, @RequestPart List<MultipartFile> files){
        AttachFileGroupDto.Response attachFileGroupDto = attachFileService.multiUpload(files, null);

        if(attachFileGroupDto != null){
            requestDto.setAttachFileGroupId(attachFileGroupDto.getId());
        }

        ContentsDto.Response dto = contentsManagementService.saveContents(requestDto);

        HttpUtil.alertAndRedirect(response, "/cms/contents-manage/view/" + dto.getId(), "정상 처리 되었습니다.", null);
    }

}
