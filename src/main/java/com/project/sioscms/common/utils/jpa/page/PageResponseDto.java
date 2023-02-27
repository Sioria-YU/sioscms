package com.project.sioscms.common.utils.jpa.page;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PageResponseDto {
    private long totalCount;    //총 컨텐츠 개수
    private int totalPageSize;  //총 페이지 사이즈
    private int pageNumber;     //현재 페이지 번호
    private int pageOffset;     //한 페이지에 표시할 콘텐츠 개수
    private int pageSize;       //한번에 표시할 페이지 개수
    private boolean isNext = false; //다음 페이지 목록 여부
    private boolean isPrev = false; //이전 페이지 목록 여부
}
