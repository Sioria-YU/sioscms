package com.project.sioscms.apps.code.domain.dto;

import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.apps.code.domain.entity.Code;
import com.project.sioscms.apps.code.domain.entity.CodeGroup;
import com.project.sioscms.common.domain.dto.CommonSearchDto;
import lombok.Data;

import java.time.LocalDateTime;

public class CodeDto {

    @Data
    public static class Request extends CommonSearchDto{
        private Long id;

        private String codeId;

        private String codeGroupId;

        private String codeLabel;

        private String option1;

        private String option2;

        private String option3;

        private String option4;

        private Integer orderNum;

        private Boolean isDeleted;

        private Boolean isUsed;

        private LocalDateTime createdDateTime;

        private LocalDateTime updatedDateTime;
    }

    @Data
    public static class Response{
        private Long id;

        private String codeId;

        private String codeLabel;

        private String option1;

        private String option2;

        private String option3;

        private String option4;

        private Integer orderNum;

        private Boolean isDeleted;

        private Boolean isUsed;

        private Account createdBy;

        private Account updatedBy;

        private LocalDateTime createdDateTime;

        private LocalDateTime updatedDateTime;
    }
}
