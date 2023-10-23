package com.project.sioscms.apps.contents.domain.dto;

import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.common.domain.dto.CommonSearchDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ContentsDto {

    @Getter
    @Setter
    public static class Request extends CommonSearchDto{
        private Long id;
        private String contentName;
        private String title;
        private String content;

        private LocalDateTime createdDateTime;
        private LocalDateTime updatedDateTine;

    }

    @Getter
    @Setter
    public static class Response{
        private Long id;
        private String contentName;
        private String title;
        private String content;
        private Account createdBy;
        private Account updatedBy;
        private LocalDateTime createdDateTime;
        private LocalDateTime updatedDateTine;
    }
}
