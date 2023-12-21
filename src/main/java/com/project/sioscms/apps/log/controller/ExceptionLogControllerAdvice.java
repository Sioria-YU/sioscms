package com.project.sioscms.apps.log.controller;

import com.project.sioscms.apps.log.service.ExceptionLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionLogControllerAdvice {

    private final ExceptionLogService exceptionLogService;

    @ExceptionHandler(Exception.class)
    public void RuntimeException(HttpServletRequest request, Exception e){
        //log saved
        exceptionLogService.save(request, e);
    }
}
