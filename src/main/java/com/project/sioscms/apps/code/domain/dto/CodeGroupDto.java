package com.project.sioscms.apps.code.domain.dto;

import com.project.sioscms.common.domain.dto.CommonSearchDto;
import lombok.Data;

import java.time.LocalDateTime;

public class CodeGroupDto {

    @Data
    public static class Request extends CommonSearchDto{
        private String codeGroup;
        private String codeGroupLabel;
        private Boolean isDeleted;
        private Long createdBy;
        private Long updatedBy;
        private LocalDateTime createdDateTime;
        private LocalDateTime updatedDateTine;
    }

    @Data
    public static class Response{
        private String codeGroup;
        private String codeGroupLabel;
        private Boolean isDeleted;
        private Long createdBy;
        private Long updatedBy;
        private LocalDateTime createdDateTime;
        private LocalDateTime updatedDateTine;
    }
}
