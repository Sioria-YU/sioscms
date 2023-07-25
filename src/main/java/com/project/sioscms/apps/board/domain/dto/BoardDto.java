package com.project.sioscms.apps.board.domain.dto;

import com.project.sioscms.apps.attach.domain.entity.AttachFileGroup;
import com.project.sioscms.apps.board.domain.entity.BoardMaster;
import com.project.sioscms.common.domain.dto.CommonSearchDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

public class BoardDto {

    @Getter
    @Setter
    public static class Request extends CommonSearchDto{
        private Long id;

        private Long boardMasterId;

        private String title;

        private String content;

        private String contentWithoutHtml;

        private String option1;

        private String option2;

        private String option3;

        private String option4;

        private String option5;

        private Long viewCount;

        private Long imageFileGroupId;

        private Long attachFileGroupId;

        private Set<String> hashtagList;

        private Boolean isDeleted;
    }

    @Getter
    @Setter
    public static class Response{
        private Long id;

        private Long createdBy;

        private Long updatedBy;

        private LocalDateTime createdDateTime;

        private LocalDateTime updatedDateTime;

        private BoardMaster boardMaster;

        private String title;

        private String content;

        private String contentWithoutHtml;

        private String option1;

        private String option2;

        private String option3;

        private String option4;

        private String option5;

        private Long viewCount;

        private AttachFileGroup imageFileGroup;

        private AttachFileGroup attachFileGroup;

        private Set<String> hashtagList;

        private Boolean isDeleted;
    }
}
