package com.project.sioscms.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {

    @RequestMapping(value = {"/","/main"})
    public String main(HttpServletRequest request, HttpServletResponse response) throws Exception{
        return "main/main";
    }
}
