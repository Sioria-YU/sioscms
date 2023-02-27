package com.project.sioscms.common.utils.jpa.page;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PageResponseDto {
    private long totalCount;
    private int totalPageSize;
    private int pageNumber;
    private int pageOffset;
    private int pageSize;
    private boolean isNext = false;
    private boolean isPrev = false;
}
