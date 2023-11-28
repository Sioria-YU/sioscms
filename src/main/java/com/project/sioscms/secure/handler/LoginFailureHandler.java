package com.project.sioscms.secure.handler;

import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.apps.account.service.AccountService;
import com.project.sioscms.apps.log.domain.dto.LoginLogDto;
import com.project.sioscms.apps.log.service.LoginLogService;
import com.project.sioscms.common.utils.common.http.HttpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private AccountService accountService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String userId = request.getParameter("userId");
        //로그인 실패 기록 저장
        LoginLogDto.Request requestDto = new LoginLogDto.Request();
        requestDto.setUserId(userId);
        requestDto.setIsSuccess(false);
        loginLogService.saveLoginLog(requestDto);

        long failCnt = 0L;
        //로그인 실패 횟수 카운팅
        Account account = accountService.findByUserId(userId).orElse(null);
        if(account != null){
            failCnt = account.getLoginFailedCount() + 1;
            //로그인 실패 5회 이상일 경우 계정 잠금 처리
            if(!account.getIsLocked()) {    //잠금 상태 아닐 경우에만 업데이트
                boolean isLocked = (failCnt > 5);
                accountService.updateLoginFailed(account, failCnt, isLocked);
            }
        }

        String message = "";
        if(failCnt > 5 || exception instanceof DisabledException){
            message = "5회 이상 로그인에 실패하여 계정이 일시적으로 잠금처리 되었습니다.\n30분 뒤 다시 시도해 주세요.";
        }else{
            if(exception instanceof BadCredentialsException){
                message = "아이디 혹은 비밀번호가 일치하지 않습니다. 5회 이상 실패할 경우 계정이 잠금처리 됩니다.(현재 " + failCnt +"회)";
            }else if(exception instanceof UsernameNotFoundException){
                message = "존재하지 않는 ID입니다.";
            }else{
                message += "로그인 실패하였습니다. 5회 이상 실패할 경우 계정이 잠금처리 됩니다.(현재 " + failCnt +"회)";
            }
        }

        request.setAttribute("exceptionMsg", message);
        request.getRequestDispatcher("/cms/auth/login").forward(request, response);
    }
}
