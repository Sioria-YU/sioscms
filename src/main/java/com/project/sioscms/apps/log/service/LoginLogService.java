package com.project.sioscms.apps.log.service;

import com.project.sioscms.apps.log.domain.dto.LoginLogDto;
import com.project.sioscms.apps.log.domain.entity.LoginLog;
import com.project.sioscms.apps.log.domain.repository.LoginLogRepository;
import com.project.sioscms.apps.log.mapper.LoginLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginLogService {

    private final LoginLogRepository loginLogRepository;

    @Transactional
    public LoginLogDto.Response saveLoginLog(LoginLogDto.Request requestDto){
        LoginLog loginLog = LoginLogMapper.mapper.toEntity(requestDto);
        loginLogRepository.save(loginLog);

        System.out.println("테스트");
        System.out.println("테스트");
        System.out.println("테스트");

        return loginLog.toResponse();
    }


}
