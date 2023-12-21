package com.project.sioscms.apps.log.service;

import com.project.sioscms.apps.log.domain.entity.ExceptionLog;
import com.project.sioscms.apps.log.domain.repository.ExceptionLogRepository;
import com.project.sioscms.common.utils.common.http.HttpUtil;
import com.project.sioscms.secure.domain.UserAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExceptionLogService {

    private final ExceptionLogRepository exceptionLogRepository;

    @Transactional
    public void save(HttpServletRequest request, Exception e){
        if(request == null || e == null){
            throw new RuntimeException();
        }

        ExceptionLog exceptionLog = new ExceptionLog();
        exceptionLog.setExceptionName(e.getClass().getSimpleName());
        exceptionLog.setExceptionRecord(e.toString());
        exceptionLog.setConnectedIp(HttpUtil.getClientIp(request));
        exceptionLog.setConnectedUrl(request.getRequestURI());

        //로그인 사용자 정보 취득
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            UserAccount userAccount = (UserAccount) authentication.getPrincipal();
            if(userAccount != null){
                exceptionLog.setUserId(userAccount.getAccount().getUserId());
            }
        }

        exceptionLogRepository.save(exceptionLog);
    }

}
