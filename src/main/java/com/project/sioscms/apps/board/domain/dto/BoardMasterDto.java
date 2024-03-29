package com.project.sioscms.apps.board.domain.dto;

import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.apps.code.domain.entity.Code;
import com.project.sioscms.common.domain.dto.CommonSearchDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class BoardMasterDto {

    @Getter
    @Setter
    public static class Request extends CommonSearchDto {
        private Long id;

        private String boardName;

        private Code boardTypeCode;

        private Boolean isDeleted;
    }

    @Getter
    @Setter
    public static class Response{
        private Long id;

        private Account createdBy;

        private Account updatedBy;

        private LocalDateTime createdDateTime;

        private LocalDateTime updatedDateTime;

        private String boardName;

        private Code boardTypeCode;

        private Boolean isDeleted;
    }
}
