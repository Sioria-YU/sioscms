package com.project.sioscms.apps.attach.domain.dto;

import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.common.domain.dto.CommonSearchDto;
import lombok.Data;
import java.time.LocalDateTime;

public class AttachFileDto {

    @Data
    public static class Request extends CommonSearchDto {
        private Long id;
        private Long attachFileGroupId;
        private String fileName;
        private String originFileName;
        private String filePath;
        private Long fileSize;
        private String fileExtension;
        private Long fileOrder;
        private Boolean isDeleted;
        private Boolean isUsed;
    }

    @Data
    public static class Response{
        private Long id;
        private Long attachFileGroupId;
        private String fileName;
        private String originFileName;
        private String filePath;
        private Long fileSize;
        private String fileExtension;
        private Long fileOrder;
        private Boolean isDeleted;
        private Boolean isUsed;
        private Account createdBy;
        private Account updatedBy;
        private LocalDateTime createdDateTime;
        private LocalDateTime updatedDateTine;
    }
}
