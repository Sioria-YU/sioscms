package com.project.sioscms.secure.handler;

import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.apps.account.service.AccountService;
import com.project.sioscms.apps.log.domain.dto.LoginLogDto;
import com.project.sioscms.apps.log.service.LoginLogService;
import com.project.sioscms.common.utils.common.http.HttpUtil;
import com.project.sioscms.secure.domain.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private AccountService accountService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();

        //로그인 성공 로그 기록
        LoginLogDto.Request requestDto = new LoginLogDto.Request();
        requestDto.setUserId(userAccount.getAccount().getUserId());
        requestDto.setIsSuccess(true);
        requestDto.setConnectedIp(HttpUtil.getClientIp(request));
        loginLogService.saveLoginLog(requestDto);

        //로그인 실패회수 초기화
        accountService.updateLoginSuccess(userAccount.getAccount());

        String url;
        if (userAccount.getAccount().getRole().compareTo(Account.Role_Type.ADMIN) == 0){
            url = "/cms/main";
        }else{
            url = "/main";
        }

        response.sendRedirect(url);
    }
}
