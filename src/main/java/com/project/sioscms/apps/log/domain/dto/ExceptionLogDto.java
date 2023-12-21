package com.project.sioscms.apps.log.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ExceptionLogDto {

    @Getter
    @Setter
    public static class Request{
        private String userId;
        private String connectedIp;
        private String connectedUrl;
        private String exceptionName;
        private String exceptionRecord;
    }

    @Getter
    @Setter
    public static class Response{
        private Long id;
        private LocalDateTime createdDateTime;
        private String userId;
        private String connectedIp;
        private String connectedUrl;
        private String exceptionName;
        private String exceptionRecord;
    }
}
