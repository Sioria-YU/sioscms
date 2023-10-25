package com.project.sioscms.apps.contents.domain.dto;

import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.common.domain.dto.CommonSearchDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ContentsHistoryDto {

    @Getter
    @Setter
    public static class Request extends CommonSearchDto{
        private Long id;
        private String content;
        private Boolean isUsed;
        private LocalDateTime createdDateTime;
        private LocalDateTime updatedDateTine;

    }

    @Getter
    @Setter
    public static class Response{
        private Long id;
        private String content;
        private Long version;
        private Boolean isUsed;
        private Boolean isDeleted;
        private Account createdBy;
        private Account updatedBy;
        private LocalDateTime createdDateTime;
        private LocalDateTime updatedDateTine;
    }
}
