package com.project.sioscms.apps.log.domain.dto;

import com.project.sioscms.apps.account.domain.entity.Account;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class LoginLogDto {

    @Getter
    @Setter
    public static class Request{
        private Long id;
        private LocalDateTime createdDateTime;
        private Boolean isSuccess;
        private String userId;
        private String connectedIp;
    }

    @Getter
    @Setter
    public static class Response{
        private Long id;
        private LocalDateTime createdDateTime;
        private Boolean isSuccess;
        private String userId;
        private String connectedIp;
    }
}
