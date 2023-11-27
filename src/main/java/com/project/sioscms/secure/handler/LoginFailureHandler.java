package com.project.sioscms.secure.handler;

import com.project.sioscms.common.utils.common.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String userId = request.getParameter("userId");
        //로그인 실패 기록 저장


        log.error("로그인 실패!!!");
        int failCnt = 0;
        //로그인 실패 횟수 카운팅

        //로그인 실패 5회 이상일 경우 계정 잠금 처리

        String message = "";

        if(failCnt > 5){
            message = "5회 이상 로그인에 실패하여 계정이 일시적으로 잠금처리 되었습니다.\n30분 뒤 다시 시도해 주세요.";
        }else{
            if(exception instanceof BadCredentialsException){
                message = "아이디 혹은 비밀번호가 일치하지 않습니다.";
            }else if(exception instanceof UsernameNotFoundException){
                message = "존재하지 않는 ID입니다.";
            }

            message += "5회 이상 실패할 경우 계정이 잠금처리 됩니다.(현재 " + failCnt +"회)";
        }

        request.setAttribute("exceptionMsg", message);
        request.getRequestDispatcher("/cms/auth/login").forward(request, response);
//        HttpUtil.alertAndRedirect(response, "/cms/auth/login", message, null);
    }
}
