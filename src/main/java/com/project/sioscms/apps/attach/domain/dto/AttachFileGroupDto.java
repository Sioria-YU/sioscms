package com.project.sioscms.apps.attach.domain.dto;

import com.project.sioscms.common.domain.dto.CommonSearchDto;
import lombok.Data;

import java.time.LocalDateTime;

public class AttachFileGroupDto {

    @Data
    public static class Request extends CommonSearchDto{
        private Long id;
        private Boolean isDeleted;
        private Long createdBy;
        private Long updatedBy;
        private LocalDateTime createdDateTime;
        private LocalDateTime updatedDateTine;
    }

    @Data
    public static class Response{
        private Long id;
        private Boolean isDeleted;
        private Long createdBy;
        private Long updatedBy;
        private LocalDateTime createdDateTime;
        private LocalDateTime updatedDateTine;
    }
}
