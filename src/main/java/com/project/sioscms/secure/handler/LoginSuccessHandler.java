package com.project.sioscms.secure.handler;

import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.secure.domain.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();

        log.info("로그인 성공!!!");
        //로그인 성공 로그 기록

        String url;
        if (userAccount.getAccount().getRole().compareTo(Account.Role_Type.ADMIN) == 0){
            url = "/cms/main";
        }else{
            url = "/main";
        }

        response.sendRedirect(url);
    }
}
