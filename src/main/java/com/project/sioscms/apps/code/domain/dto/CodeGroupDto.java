package com.project.sioscms.apps.code.domain.dto;

import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.common.domain.dto.CommonSearchDto;
import lombok.Data;

import java.time.LocalDateTime;

public class CodeGroupDto {

    @Data
    public static class Request extends CommonSearchDto{
        private String codeGroupId;
        private String codeGroupLabel;
        private String codeGroupNote;
        private Boolean isDeleted;
        private Boolean isUsed;
        private LocalDateTime createdDateTime;
        private LocalDateTime updatedDateTine;
    }

    @Data
    public static class Response{
        private String codeGroupId;
        private String codeGroupLabel;
        private String codeGroupNote;
        private Boolean isDeleted;
        private Boolean isUsed;
        private Account createdBy;
        private Account updatedBy;
        private LocalDateTime createdDateTime;
        private LocalDateTime updatedDateTine;
    }
}
