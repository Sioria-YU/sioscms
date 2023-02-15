package com.project.sioscms.common.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class WebMvcErrorPageController implements ErrorController {
    private final Logger LOGGER = LoggerFactory.getLogger(WebMvcErrorPageController.class);

    @RequestMapping(value = {"/common/error", "/error"})
    public String setError() {
//        LOGGER.info("error page 연결 - 잘못된 URL");
        //json error 데이터를 보낼 경우 아래 소스 수정하여 적용
//        Response res = new Response();
//        Header header = new Header();
//        header.setResultCode(Integer.valueOf(PropertiesUtils.getMessage("code.fail.url")));
//        header.setResultMsg(PropertiesUtils.getMessage("msg.fail.url"));
//        res.setHeader(header);
//        return  CmmnVar.GSON.toJson(res);
        return "common/error/error";
    }
}
