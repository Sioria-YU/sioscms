package com.project.sioscms.apps.hashtag.domain.dto;

import com.project.sioscms.common.domain.dto.CommonSearchDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class HashtagDto {

    @Getter
    @Setter
    public static class Request extends CommonSearchDto{
        private Long id;
        private String hashtagName;
        private Boolean isDeleted;
        private Boolean isDisplay;
    }

    @Getter
    @Setter
    public static class Response{
        private Long id;
        private String hashtagName;
        private Boolean isDeleted;
        private Boolean isDisplay;
        private LocalDateTime createdDateTime;
        private LocalDateTime updatedDateTime;
    }
}
