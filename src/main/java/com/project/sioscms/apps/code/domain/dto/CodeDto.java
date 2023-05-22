package com.project.sioscms.apps.code.domain.dto;

import com.project.sioscms.apps.code.domain.entity.CodeGroup;
import com.project.sioscms.common.domain.dto.CommonSearchDto;
import lombok.Data;

import java.time.LocalDateTime;

public class CodeDto {

    @Data
    public static class Request extends CommonSearchDto{
        private String codeId;

        private String codeGroupId;

        private String codeLabel;

        private String option1;

        private String option2;

        private String option3;

        private String option4;

        private Integer orderNum;

        private Boolean isDeleted;

        private Long createdBy;

        private Long updatedBy;

        private LocalDateTime createdDateTime;

        private LocalDateTime updatedDateTime;
    }

    @Data
    public static class Response{
        private String codeId;

        private CodeGroup codeGroup;

        private String codeLabel;

        private String option1;

        private String option2;

        private String option3;

        private String option4;

        private Integer orderNum;

        private Boolean isDeleted;

        private Long createdBy;

        private Long updatedBy;

        private LocalDateTime createdDateTime;

        private LocalDateTime updatedDateTime;
    }
}
